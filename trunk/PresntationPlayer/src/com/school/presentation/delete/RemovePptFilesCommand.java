package com.school.presentation.delete;

import java.io.File;
import java.io.IOException;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.io.FileUtils;

import com.school.model.DetailedPresentation;
import com.school.model.Presentation;
import com.school.presentation.convert.ConverterContext;
import com.school.presentation.exceptions.CommandFailedToExecuteExeption;

public class RemovePptFilesCommand implements Command {

	@Override
	public boolean execute(Context context) throws Exception {
		if (!context.containsKey(ConverterContext.DETAILED_PRESENTATION)
				|| !context.containsKey(ConverterContext.REPO_HOME)
				|| !context.containsKey(ConverterContext.REPO_CONVERTED)) {
			throw new CommandFailedToExecuteExeption();
		}
		String repositoryHome = (String) context.get(ConverterContext.REPO_HOME);
		String convertedRepositoryPath = (String) context.get(ConverterContext.REPO_CONVERTED);
		DetailedPresentation presentation = (DetailedPresentation) context.get(ConverterContext.DETAILED_PRESENTATION);
		deleteFilesAndFolders(repositoryHome, convertedRepositoryPath, presentation);
		return false;
	}

	private void deleteFilesAndFolders(String repositoryHome, String convertedRepositoryPath, Presentation presentation)
			throws IOException {
		String fileUrl = repositoryHome + "/" + convertedRepositoryPath + "/" + presentation.getRepositoryName();
		File dir = new File(fileUrl);
		if (dir.exists() && dir.isDirectory()) {
			FileUtils.cleanDirectory(dir);
			FileUtils.deleteDirectory(dir);
		}
	}

}
