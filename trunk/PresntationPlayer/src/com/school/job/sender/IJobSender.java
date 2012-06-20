package com.school.job.sender;

import com.school.job.Job;

public interface IJobSender {
	public void send(final Job job,final String queueName);
}
