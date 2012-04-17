package com.school.presentation.converter.commands;

import java.io.File;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.artofsolving.jodconverter.OfficeDocumentConverter;

import com.school.exceptions.CommandFailedToExecuteExeption;
import com.school.model.Presentation;
import com.school.presentation.converter.impl.ConverterContext;
import com.school.service.OfficeManagerService;
import com.school.util.OtherUtils;

public class Convert2Pdf implements Command {

	@Override
	public boolean execute(Context context) throws Exception {
		System.err.println("officeManager" + OfficeManagerService.officeManager + " convert 2 pdf ");
		if (context.containsKey(ConverterContext.PRESENTATION)) {
			String repositoryHome = (String) context.get(ConverterContext.REPO_HOME);
			Presentation data = (Presentation) context.get(ConverterContext.PRESENTATION);
			File outFile = createOutputFile(data, repositoryHome, context);
			File inputFile = getInputFIle(data, repositoryHome);
			OfficeDocumentConverter converter = new OfficeDocumentConverter(OfficeManagerService.officeManager);
			converter.convert(inputFile, outFile);
		} else {
			throw new CommandFailedToExecuteExeption();
		}
		return false;
	}

	private File getInputFIle(Presentation data, String repositoryHome) {
		String inputUrl = OtherUtils.concatStringsAscending(repositoryHome, "/", data.getRepositoryPath(), "/",
				data.getRepositoryName(), ".", data.getOriginalExtension());
		return new File(inputUrl);
	}

	@SuppressWarnings("unchecked")
	private File createOutputFile(Presentation data, String repositoryHome, Context context) {
		String convertedRepositoryPath = (String) context.get(ConverterContext.REPO_CONVERTED);
		String outUrl = OtherUtils.concatStringsAscending(repositoryHome, "/", convertedRepositoryPath, "/",
				data.getRepositoryName(), "/", data.getRepositoryName(), ".", OtherUtils.PDF_FILE_EXTENSION);
		context.put(ConverterContext.PDF_CONVERSION_URL, outUrl);
		return new File(outUrl);
	}

}
