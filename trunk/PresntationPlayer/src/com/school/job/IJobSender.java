package com.school.job;

public interface IJobSender {
	public void send(final Job job,final String queueName);
}
