package com.school.upload.job;

import org.springframework.jms.core.JmsTemplate;

public class JobSenderImpl implements IJobSender {
	private final JmsTemplate jmsTemplate;

	public JobSenderImpl(final JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	@Override
	public void send(final Job uploadJob) {
		jmsTemplate.convertAndSend(uploadJob);
	}
}
