package com.school.upload.job;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.school.converter.Converter;
import com.school.converter.ConverterContext;

public class JobConsumer implements MessageListener {
	Converter converter;
	
	@Override
	public void onMessage(final Message message) {
		if (message instanceof ObjectMessage) {
			ObjectMessage objMessage = (ObjectMessage) message;
			try {
				
				Job uplloadJob = (Job) objMessage.getObject();
				System.out.println("###### --- start :"+uplloadJob.getUploadFileId());
				ConverterContext converterContext=getContextForUploadedFile(uplloadJob);
				converter.convert(converterContext);
				System.out.println("###### --- stop: "+uplloadJob.getUploadFileId());
			} catch (final JMSException e) {
				e.printStackTrace();
			}
		}
	}

	private ConverterContext getContextForUploadedFile(Job uplloadJob) {
		//get from db th object
		return new ConverterContext();
	}

	public void setConverter(Converter converter) {
		this.converter = converter;
	}
	
	
}
