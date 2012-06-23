package com.school.job.consumers;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.school.dao.BaseDao;
import com.school.job.Job;
import com.school.model.Email;
import com.school.util.ConfigurationLoader;

public class EmailJobConsumer extends JobConsumer {
	BaseDao baseDao;

	@Override
	public void execute(Job job) {
		Email email = baseDao.getEntity(job.getjobId(), Email.class);
		try {
			sendEmail(email);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void sendEmail(Email email) throws MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(host);
		sender.setUsername(username);
		sender.setPassword(password);
		sender.setPort(587);
		sender.setJavaMailProperties(props);
		
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(email.getEmailAddress());
		helper.setSubject(email.getSubject());
		helper.setText(email.getContent());
		sender.send(message);
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	private final String host = ConfigurationLoader.getConfig().getString("email.config.host"),
			username=ConfigurationLoader.getConfig().getString("email.config.username"),
			password=ConfigurationLoader.getConfig().getString("email.config.password");
}
