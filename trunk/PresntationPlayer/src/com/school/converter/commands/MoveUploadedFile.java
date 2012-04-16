package com.school.converter.commands;

import java.io.File;
import java.io.IOException;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.school.converter.impl.ConverterContext;
import com.school.exceptions.CommandFailedToExecuteExeption;
import com.school.model.UploadedPresentationData;
import com.school.util.OtherUtils;

public class MoveUploadedFile implements Command {

	@Override
	public boolean execute(Context context) throws Exception {
		if (context.containsKey(ConverterContext.UPLOADED_DATA)
				&& context.containsKey(ConverterContext.PDF_CONVERSION_URL)) {

			String repoHome = (String) context.get(ConverterContext.REPO_HOME);
			String convertedRepositoryPath = (String) context.get(ConverterContext.REPO_CONVERTED);
			UploadedPresentationData data = (UploadedPresentationData) context.get(ConverterContext.UPLOADED_DATA);
			if (data != null && StringUtils.isNotEmpty(repoHome) && StringUtils.isNotEmpty(convertedRepositoryPath)) {
				moveUploadedFile(repoHome, convertedRepositoryPath, data);
			} else {
				throw new CommandFailedToExecuteExeption();
			}
		} else {
			throw new CommandFailedToExecuteExeption();
		}
		return false;
	}

	private void moveUploadedFile(String repoHome, String convertedRepositoryPath, UploadedPresentationData data)
			throws CommandFailedToExecuteExeption {
		try {
			String fileName = OtherUtils.concatStringsAscending(data.getRepositoryName(), ".",
					data.getOriginalExtension());
			String inputUrl = OtherUtils.concatStringsAscending(repoHome, "/", data.getRepositoryPath(), "/", fileName);
			String outputUrl = OtherUtils.concatStringsAscending(repoHome, "/", convertedRepositoryPath,"/",data.getRepositoryName(), "/", fileName);
			File inputFile = new File(inputUrl);
			File outputFile = new File(outputUrl);
			FileUtils.moveFile(inputFile, outputFile);
		} catch (IOException e) {
			throw new CommandFailedToExecuteExeption();
		}
	}

}
