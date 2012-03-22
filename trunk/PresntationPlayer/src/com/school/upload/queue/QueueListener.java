package com.school.upload.queue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.stereotype.Component;

import com.school.converter.Converter;
import com.school.converter.ConverterContext;

@Component
public class QueueListener implements MessageListener {
	Converter converter;
	
	public void onMessage(final Message message) {
		if (message instanceof ObjectMessage) {
			ObjectMessage objMessage = (ObjectMessage) message;
			try {
				
				Long uplloadFileId = (Long) objMessage.getObject();
				System.out.println("###### --- start :"+uplloadFileId);
				ConverterContext converterContext=getContextForUploadedFile(uplloadFileId);
				converter.convert(converterContext);
				System.out.println("###### --- stop: "+uplloadFileId);
			} catch (final JMSException e) {
				e.printStackTrace();
			}
		}
	}

	private ConverterContext getContextForUploadedFile(Long uplloadFileId) {
		//get from db th object
		return new ConverterContext();
	}

	public void setConverter(Converter converter) {
		this.converter = converter;
	}
	
	
}
