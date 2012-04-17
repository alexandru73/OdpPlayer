package com.school.presentation.converter.commands;

import com.school.model.Email;
import com.school.model.Presentation;
import com.school.presentation.AbstractSendNotification;
import com.school.util.ConfigurationLoader;

public class SendNotificationSuccess extends AbstractSendNotification {

	@Override
	protected Email setupEmail(Presentation presentation) {
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
		email.setId(baseDao.save(email));
		return email;
	}
}
