package com.school.converter.commands;

import java.io.File;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.io.FileUtils;
import org.artofsolving.jodconverter.OfficeDocumentConverter;

import com.school.converter.impl.ConverterContext;
import com.school.exceptions.CommandFailedToExecuteExeption;
import com.school.model.Presentation;
import com.school.model.UploadedPresentationData;
import com.school.service.OfficeManagerService;
import com.school.util.ConfigurationLoader;
import com.school.util.OtherUtils;

public class Convert2Pdf implements Command {

	@Override
	public boolean execute(Context context) throws Exception {
		System.err.println("officeManager" + OfficeManagerService.officeManager + " convert 2 pdf ");
		if (context.containsKey(ConverterContext.UPLOADED_DATA)) {
			String repositoryHome = (String) context.get(ConverterContext.REPO_HOME);
			UploadedPresentationData data = (UploadedPresentationData) context.get(ConverterContext.UPLOADED_DATA);
			File outFile = createOutputFile(data, repositoryHome, context);
			File inputFile = getInputFIle(data, repositoryHome);
			OfficeDocumentConverter converter = new OfficeDocumentConverter(OfficeManagerService.officeManager);
			converter.convert(inputFile, outFile);
		} else {
			throw new CommandFailedToExecuteExeption();
		}
		return false;
	}

	private File getInputFIle(UploadedPresentationData data, String repositoryHome) {
		String inputUrl = OtherUtils.concatStringsAscending(repositoryHome, "/", data.getRepositoryPath(), "/",
				data.getRepositoryName(), ".", data.getOriginalExtension());
		return new File(inputUrl);
	}

	@SuppressWarnings("unchecked")
	private File createOutputFile(UploadedPresentationData data, String repositoryHome, Context context) {
		String convertedRepositoryPath = (String) context.get(ConverterContext.REPO_CONVERTED);
		String outUrl = OtherUtils.concatStringsAscending(repositoryHome, "/", convertedRepositoryPath, "/",
				data.getRepositoryName(), "/", data.getRepositoryName(), ".", OtherUtils.PDF_FILE_EXTENSION);
		context.put(ConverterContext.PDF_CONVERSION_URL, outUrl);
		return new File(outUrl);
	}

}
