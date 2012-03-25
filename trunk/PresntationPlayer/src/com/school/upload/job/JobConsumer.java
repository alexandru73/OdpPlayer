package com.school.upload.job;

import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;

import com.school.converter.Converter;

public class JobConsumer implements MessageListener {
	Converter converter;
	
	@Override
	public void onMessage(final Message message) {
		if (message instanceof ObjectMessage) {
			ObjectMessage objMessage = (ObjectMessage) message;
			try {
				
				Job uplloadJob = (Job) objMessage.getObject();
				System.out.println("###### --- start :"+uplloadJob.getUploadFileId());
				Context converterContext=getContextForUploadedFile(uplloadJob);
				converter.convert(converterContext);
				System.out.println("###### --- stop: "+uplloadJob.getUploadFileId());
				
			} catch (final JMSException e) {
				e.printStackTrace();
			}
		}
	}

	private  Context getContextForUploadedFile(Job uplloadJob) {
		Context context=new ContextBase();		
		return context ;
	}

	public void setConverter(Converter converter) {
		this.converter = converter;
	}
	

	private static final int BASE_PORT = 8010;
	private static AtomicInteger portNumber = new AtomicInteger(BASE_PORT);
}
