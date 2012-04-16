package com.school.converter.commands;

import java.io.File;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang.StringUtils;

import com.school.converter.impl.ConverterContext;
import com.school.exceptions.CommandFailedToExecuteExeption;
import com.school.exceptions.SvgConversionFailedException;
import com.school.model.UploadedPresentationData;
import com.school.util.OtherUtils;

public class ConvertPdf2Svg implements Command {
	@Override
	public boolean execute(Context context) throws Exception {
		if (context.containsKey(ConverterContext.UPLOADED_DATA)
				&& context.containsKey(ConverterContext.PDF_CONVERSION_URL)) {
			String commandLine = createCommandLine(context);
			System.err.println(commandLine);
			Process svgConverterProcess = Runtime.getRuntime().exec(commandLine);
			int exitCode = svgConverterProcess.waitFor();
			if (exitCode != 0) {
				throw new SvgConversionFailedException();
			}
		} else {
			throw new CommandFailedToExecuteExeption();
		}
		return false;
	}

	private String createCommandLine(Context context) throws CommandFailedToExecuteExeption {
		UploadedPresentationData data = (UploadedPresentationData) context.get(ConverterContext.UPLOADED_DATA);
		String imgFolderPath = createSvgImageFolder(context, data);
		String pdfFilePath = (String) context.get(ConverterContext.PDF_CONVERSION_URL);
		if (data != null && StringUtils.isNotEmpty(pdfFilePath) && StringUtils.isNotEmpty(imgFolderPath)) {
			String commandLine = OtherUtils.concatStringsAscending("pdf2svg  ", pdfFilePath, " ", imgFolderPath, "/",
					data.getRepositoryName(), "%d.svg all");
			return commandLine;
		} else {
			throw new CommandFailedToExecuteExeption();
		}
	}

	private String createSvgImageFolder(Context context, UploadedPresentationData data)
			throws CommandFailedToExecuteExeption {
		String repoHome = (String) context.get(ConverterContext.REPO_HOME);
		String convertedRepositoryPath = (String) context.get(ConverterContext.REPO_CONVERTED);
		if (data != null && StringUtils.isNotEmpty(repoHome) && StringUtils.isNotEmpty(convertedRepositoryPath)) {
			String imgFolderPath = OtherUtils.concatStringsAscending(repoHome, "/", convertedRepositoryPath, "/",
					data.getRepositoryName(), "/svg-folder");
			File imgDir = new File(imgFolderPath);
			imgDir.mkdir();
			return imgFolderPath;
		} else {
			throw new CommandFailedToExecuteExeption();
		}
	}
}
