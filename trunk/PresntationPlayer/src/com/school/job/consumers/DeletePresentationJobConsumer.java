package com.school.job.consumers;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;

import com.school.dao.BaseDao;
import com.school.job.Job;
import com.school.model.DetailedPresentation;
import com.school.presentation.convert.ConverterContext;
import com.school.presentation.exceptions.CommandNotFoundException;
import com.school.presentation.exceptions.CommandRollbackFailedException;
import com.school.run.CommandRunner;
import com.school.util.ConfigurationLoader;

public class DeletePresentationJobConsumer extends JobConsumer {
	BaseDao baseDao;
	CommandRunner commandRunner;

	@Override
	public void execute(Job job) {
		Context commandContext = getContextForUploadedFile(job);
		try {
			commandRunner.executeCommand(DELETE_COMMAND, DELETE_ROLLBACK, commandContext);
		} catch (CommandNotFoundException | CommandRollbackFailedException e) {
			// TODO log this
		}
	}

	@SuppressWarnings("unchecked")
	private Context getContextForUploadedFile(Job job) {
		DetailedPresentation detailedPresentation = baseDao.getEntity(job.getjobId(), DetailedPresentation.class);
		String convertedRepo = ConfigurationLoader.getConfig().getString("local.repository.repo.path");
		String repoHome = ConfigurationLoader.getConfig().getString("local.repository.home.path");
		Context context = new ContextBase();
		context.put(ConverterContext.DETAILED_PRESENTATION, detailedPresentation);
		context.put(ConverterContext.REPO_HOME, repoHome);
		context.put(ConverterContext.REPO_CONVERTED, convertedRepo);
		return context;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void setCommandRunner(CommandRunner commandRunner) {
		this.commandRunner = commandRunner;
	}

	private static final String DELETE_COMMAND = "deletePptChain", DELETE_ROLLBACK = "deletePptRollback";
}
