package com.school.presentation.converter.commands;

import javax.annotation.Resource;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import com.school.dao.BaseDao;
import com.school.job.Job;
import com.school.job.JobSenderImpl;
import com.school.model.Email;
import com.school.util.ConfigurationLoader;

public class SendNotification implements Command{
	@Resource(name = "jobSenderImpl")
	JobSenderImpl queue;
	BaseDao baseDao;
	
	@Override
	public boolean execute(Context context) throws Exception {
		String subject="subiect";
		String emailAddress="email";
		String content="content";
		Email email=new Email(subject, emailAddress, content);
		Long id=baseDao.save(email);
		queue.send(new Job(id), NOTIFICATION_QUEUE);
		return true;
	}

	public void setQueue(JobSenderImpl queue) {
		this.queue = queue;
	}
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	private String NOTIFICATION_QUEUE = ConfigurationLoader.getConfig().getString("active.mq.queue.notification");

}
