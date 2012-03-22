package com.school.upload.queue;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.springframework.stereotype.Component;

@Component
public class JmsExceptionListener implements ExceptionListener {
	
	public void onException(final JMSException e) {
		//TODO log this treat error
		e.printStackTrace();
	}
}