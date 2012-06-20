package com.school.job.consumers;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;

import com.school.dao.BaseDao;
import com.school.job.Job;
import com.school.model.Presentation;
import com.school.presentation.convert.ConverterContext;
import com.school.presentation.exceptions.CommandNotFoundException;
import com.school.presentation.exceptions.CommandRollbackFailedException;
import com.school.run.CommandRunner;
import com.school.util.ConfigurationLoader;

public class ConvertPresentationJobConsumer extends JobConsumer {
	CommandRunner commandRunner;
	BaseDao baseDao;

	@Override
	public void execute(Job job) {
		Context converterContext = getContextForUploadedFile(job);
		try {	
			commandRunner.executeCommand(CONVERT_PRESENTATION_COMMAND, CONVERT_PRESENTATION_ROLLBACK, converterContext);
		} catch (CommandNotFoundException | CommandRollbackFailedException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private Context getContextForUploadedFile(Job uploadJob) {
		Presentation uploadData = baseDao.getEntity(uploadJob.getjobId(),
				Presentation.class);
		String convertedRepo = ConfigurationLoader.getConfig().getString("local.repository.repo.path");
		String repoHome = ConfigurationLoader.getConfig().getString("local.repository.home.path");
		Context context = new ContextBase();
		context.put(ConverterContext.PRESENTATION, uploadData);
		context.put(ConverterContext.REPO_HOME, repoHome);
		context.put(ConverterContext.REPO_CONVERTED, convertedRepo);
		return context;
	}
	
	public void setCommandRunner(CommandRunner commandRunner) {
		this.commandRunner = commandRunner;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}


	private static final String CONVERT_PRESENTATION_COMMAND = "presentationConverterChain",
			CONVERT_PRESENTATION_ROLLBACK = "presentationConverterRollback";
}
