package com.school.presentation.converter.commands;

import java.io.File;
import java.io.IOException;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.io.FileUtils;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.school.dao.BaseDao;
import com.school.job.Job;
import com.school.job.JobSenderImpl;
import com.school.model.Email;
import com.school.model.Presentation;
import com.school.model.UploadedPresentationData;
import com.school.presentation.converter.impl.ConverterContext;
import com.school.util.ConfigurationLoader;

public class ConverterRollBackCommand implements Command {
	BaseDao baseDao;
	ResourceBundleMessageSource messages;
	JobSenderImpl queue;

	@Override
	public boolean execute(Context context) throws Exception {

		if (context.containsKey(ConverterContext.UPLOADED_DATA)) {
			UploadedPresentationData data = (UploadedPresentationData) context.get(ConverterContext.UPLOADED_DATA);
			Job job = new Job(setupEmail(data));
			queue.send(job, NOTIFICATION_QUEUE);
			deleteFilesAndFolders(context, data);
			if (data.getId() != null) {
				baseDao.delete(data);
			}
		}
		if (context.containsKey(ConverterContext.PRESENTATION)) {
			Presentation presentation = (Presentation) context.get(ConverterContext.PRESENTATION);
			deleteFilesAndFolders(context, presentation);
			if (presentation.getId() != null) {
				baseDao.delete(presentation);
			}
		}
		return true;
	}

	private void deleteFilesAndFolders(Context context, UploadedPresentationData data) throws IOException {

		String repositoryHome = (String) context.get(ConverterContext.REPO_HOME);
		String uploadUrl = (String) context.get(ConverterContext.REPO_UPLOADED);
		String convertedRepositoryPath = (String) context.get(ConverterContext.REPO_CONVERTED);
		String fileUrl = repositoryHome + "/" + convertedRepositoryPath + "/" + data.getRepositoryName();
		String uploadedFileUrl = repositoryHome + "/" + uploadUrl + "/" + data.getRepositoryName();
		File upFile = new File(uploadedFileUrl);
		File dir = new File(fileUrl);
		if (upFile.exists() && upFile.isFile()) {
			FileUtils.forceDelete(upFile);
		}
		if (dir.exists() && dir.isDirectory()) {
			FileUtils.cleanDirectory(dir);
			FileUtils.deleteDirectory(dir);
		}
	}

	private Long setupEmail(UploadedPresentationData data) {
		Object[] args = new Object[2];
		args[0] = data.getTitle();
		String subject = messages.getMessage("email.conversion.failure.subject", args, null);
		String emailAddress = data.getUser().getEmail();
		StringBuilder content = new StringBuilder(messages.getMessage("email.conversion.failure.content", args, null));
		content.append("\n\n");
		args[0] = ConfigurationLoader.getConfig().getString("page.contact.url");
		content.append(messages.getMessage("email.no.reply", args, null));
		Email email = new Email(subject, emailAddress, content.toString());
		return baseDao.save(email);
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	private final String NOTIFICATION_QUEUE = ConfigurationLoader.getConfig().getString("active.mq.queue.notification");
}
