package com.school.job.consumers;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;

import com.school.dao.BaseDao;
import com.school.job.Job;
import com.school.job.JobConsumer;
import com.school.model.Presentation;
import com.school.presentation.converter.Converter;
import com.school.presentation.converter.impl.ConverterContext;
import com.school.util.ConfigurationLoader;

public class ConvertPresentationJobConsumer extends JobConsumer {
	Converter converter;
	BaseDao baseDao;

	@Override
	public void execute(Job job) {
		Context converterContext = getContextForUploadedFile(job);
		converter.convert(converterContext);
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

	public void setConverter(Converter converter) {
		this.converter = converter;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

}
