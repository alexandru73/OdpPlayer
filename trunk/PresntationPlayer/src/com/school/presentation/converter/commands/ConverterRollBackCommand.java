package com.school.presentation.converter.commands;

import java.io.File;
import java.io.IOException;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.io.FileUtils;

import com.school.dao.BaseDao;
import com.school.model.Presentation;
import com.school.model.UploadedPresentationData;
import com.school.presentation.converter.impl.ConverterContext;

public class ConverterRollBackCommand implements Command {
	BaseDao baseDao;

	@Override
	public boolean execute(Context context) throws Exception {
		if (context.containsKey(ConverterContext.UPLOADED_DATA)) {
			UploadedPresentationData data = (UploadedPresentationData) context.get(ConverterContext.UPLOADED_DATA);
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

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

}
