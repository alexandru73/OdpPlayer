package com.school.upload.queue;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

@Resource
public class QueueSender {
	private final JmsTemplate jmsTemplate;

	public QueueSender(final JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void send(final Long uploadId) {
		jmsTemplate.convertAndSend(QUEUE_NAME, uploadId);
	}
	
	private static final String QUEUE_NAME = "Queue.Name";
}
