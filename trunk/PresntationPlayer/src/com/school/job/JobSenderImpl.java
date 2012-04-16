package com.school.job;

import org.springframework.jms.core.JmsTemplate;

public class JobSenderImpl implements IJobSender {
	private final JmsTemplate jmsTemplate;

	public JobSenderImpl(final JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	@Override
	public void send(final Job job, String queueName) {
		jmsTemplate.convertAndSend(queueName, job);
	}
}
