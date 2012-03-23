package com.school.upload.job;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.springframework.stereotype.Component;

@Component
public class JobExceptionListener implements ExceptionListener {
	
	public void onException(final JMSException e) {
		//TODO log this treat error
		e.printStackTrace();
	}
}