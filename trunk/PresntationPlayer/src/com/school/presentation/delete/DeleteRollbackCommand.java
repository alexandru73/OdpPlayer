package com.school.presentation.delete;

import org.apache.commons.chain.Context;

import com.school.exceptions.CommandFailedToExecuteExeption;
import com.school.model.DetailedPresentation;
import com.school.model.Email;
import com.school.presentation.AbstractSendNotification;
import com.school.presentation.converter.impl.ConverterContext;
import com.school.util.ConfigurationLoader;

public class DeleteRollbackCommand extends AbstractSendNotification {

	@Override
	protected Email setupEmail(Context context) throws CommandFailedToExecuteExeption {
		if (!context.containsKey(ConverterContext.DETAILED_PRESENTATION)) {
			throw new CommandFailedToExecuteExeption();
		}
		DetailedPresentation presentation = (DetailedPresentation) context.get(ConverterContext.DETAILED_PRESENTATION);
		Object[] args = new Object[2];
		args[0] = presentation.getTitle();
		String subject = messages.getMessage("email.delete.failure.subject", args, null);
		
		String emailAddress = ConfigurationLoader.getConfig().getString("email.config.administrator.address");
		
		args[0] = presentation.getId();
		args[1]=presentation.getRepositoryName();
		StringBuilder content = new StringBuilder(messages.getMessage("email.delete.failure.content", args, null));
		content.append("\n\n");
		args[0] = ConfigurationLoader.getConfig().getString("page.contact.url");
		content.append(messages.getMessage("email.no.reply", args, null));
		
		Email email = new Email(subject, emailAddress, content.toString());
		email.setId(baseDao.save(email));
		return email;
	}
}
