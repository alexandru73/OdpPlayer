package com.school.presentation;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.school.dao.BaseDao;
import com.school.job.Job;
import com.school.job.sender.IJobSender;
import com.school.model.Email;
import com.school.presentation.exceptions.CommandFailedToExecuteExeption;
import com.school.util.ConfigurationLoader;

public abstract class AbstractSendNotification implements Command {
	protected ResourceBundleMessageSource messages;
	protected IJobSender queue;
	protected BaseDao baseDao;

	@Override
	public boolean execute(Context context) throws Exception {
		Email email = setupEmail(context);
		Job job = new Job(email.getId());
		queue.send(job, NOTIFICATION_QUEUE);
		return true;
	}

	protected abstract Email setupEmail(Context context) throws CommandFailedToExecuteExeption;

	public void setQueue(IJobSender queue) {
		this.queue = queue;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void setMessages(ResourceBundleMessageSource messages) {
		this.messages = messages;
	}

	protected final String NOTIFICATION_QUEUE = ConfigurationLoader.getConfig().getString(
			"active.mq.queue.notification");

}
