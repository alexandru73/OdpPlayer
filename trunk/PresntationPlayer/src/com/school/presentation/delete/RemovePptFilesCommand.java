package com.school.presentation.delete;

import java.io.File;
import java.io.IOException;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.io.FileUtils;

import com.school.model.Presentation;
import com.school.presentation.converter.impl.ConverterContext;

public class RemovePptFilesCommand implements Command {

	@Override
	public boolean execute(Context arg0) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	private void deleteFilesAndFolders(Context context, Presentation data) throws IOException {
		String repositoryHome = (String) context.get(ConverterContext.REPO_HOME);
		String convertedRepositoryPath = (String) context.get(ConverterContext.REPO_CONVERTED);
		String fileUrl = repositoryHome + "/" + convertedRepositoryPath + "/" + data.getRepositoryName();
		File dir = new File(fileUrl);
		if (dir.exists() && dir.isDirectory()) {
			FileUtils.cleanDirectory(dir);
			FileUtils.deleteDirectory(dir);
		}
	}

}
