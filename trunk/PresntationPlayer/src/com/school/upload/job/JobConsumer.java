package com.school.upload.job;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;

import com.school.converter.Converter;
import com.school.converter.impl.ConverterContext;
import com.school.dao.BaseDao;
import com.school.model.UploadedPresentationData;
import com.school.util.ConfigurationLoader;

public class JobConsumer implements MessageListener {
	Converter converter;
	BaseDao baseDao;

	@Override
	public void onMessage(final Message message) {
		if (message instanceof ObjectMessage) {
			ObjectMessage objMessage = (ObjectMessage) message;
			try {

				Job uplloadJob = (Job) objMessage.getObject();
				Context converterContext = getContextForUploadedFile(uplloadJob);
				converter.convert(converterContext);
			} catch (final JMSException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Context getContextForUploadedFile(Job uploadJob) {
		UploadedPresentationData uploadData = baseDao.getEntity(uploadJob.getUploadFileId(),
				UploadedPresentationData.class);
		String convertedRepo = ConfigurationLoader.getConfig().getString("local.repository.repo.path");
		String repoHome = ConfigurationLoader.getConfig().getString("local.repository.home.path");
		Context context = new ContextBase();
		context.put(ConverterContext.UPLOADED_DATA, uploadData);
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
