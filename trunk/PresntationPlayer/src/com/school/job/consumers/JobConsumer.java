package com.school.job.consumers;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.school.job.Job;

public abstract class JobConsumer implements MessageListener {

	@Override
	public void onMessage(final Message message) {
		if (message instanceof ObjectMessage) {
			ObjectMessage objMessage = (ObjectMessage) message;
			try {
				Job job = (Job) objMessage.getObject();
				execute(job);
			} catch (final JMSException e) {
				e.printStackTrace();
			}
		}
	}
	public abstract void execute(Job job);
}
