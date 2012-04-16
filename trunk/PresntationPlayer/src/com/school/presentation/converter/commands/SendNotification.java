package com.school.presentation.converter.commands;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.school.dao.BaseDao;
import com.school.exceptions.CommandFailedToExecuteExeption;
import com.school.job.Job;
import com.school.job.JobSenderImpl;
import com.school.model.Email;
import com.school.model.Presentation;
import com.school.presentation.converter.impl.ConverterContext;
import com.school.util.ConfigurationLoader;

public class SendNotification implements Command {
	ResourceBundleMessageSource messages;
	JobSenderImpl queue;
	BaseDao baseDao;

	@Override
	public boolean execute(Context context) throws Exception {
		if (!context.containsKey(ConverterContext.PRESENTATION)) {
			throw new CommandFailedToExecuteExeption();
		}
		Presentation presentation = (Presentation) context.get(ConverterContext.PRESENTATION);
		Long emailId = setupEmail(presentation);
		Job job = new Job(emailId);
		queue.send(job, NOTIFICATION_QUEUE);
		return true;
	}

	private Long setupEmail(Presentation presentation) {
		Object[] args = new Object[2];
		args[0] = presentation.getTitle();
		String subject = messages.getMessage("email.conversion.success.subject", args, null);
		String emailAddress = presentation.getUser().getEmail();
		args[1] = ConfigurationLoader.getConfig().getString("page.presentation.with.id.param.url")
				+ presentation.getRepositoryName();
		StringBuilder content = new StringBuilder(messages.getMessage("email.conversion.success.content", args, null));
		content.append("\n\n");
		args[0] = ConfigurationLoader.getConfig().getString("page.contact.url");
		content.append(messages.getMessage("email.no.reply", args, null));
		Email email = new Email(subject, emailAddress, content.toString());
		return baseDao.save(email);
	}

	public void setQueue(JobSenderImpl queue) {
		this.queue = queue;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void setMessages(ResourceBundleMessageSource messages) {
		this.messages = messages;
	}

	private final String NOTIFICATION_QUEUE = ConfigurationLoader.getConfig().getString("active.mq.queue.notification");

}
