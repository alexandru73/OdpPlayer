package com.school.converter.commands;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang.StringUtils;

import com.school.converter.impl.ConverterContext;
import com.school.exceptions.CommandFailedToExecuteExeption;
import com.school.model.Presentation;
import com.school.model.UploadedPresentationData;

public class ExtractMetadata implements Command {

	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Context context) throws Exception {
		if (context.containsKey(ConverterContext.UPLOADED_DATA)
				&& context.containsKey(ConverterContext.PDF_CONVERSION_URL)) {

			String convertedRepositoryPath = (String) context.get(ConverterContext.REPO_CONVERTED);
			String pdfFilePath = (String) context.get(ConverterContext.PDF_CONVERSION_URL);
			UploadedPresentationData data = (UploadedPresentationData) context.get(ConverterContext.UPLOADED_DATA);
			if (data != null && StringUtils.isNotEmpty(convertedRepositoryPath)
					&& StringUtils.isNotEmpty(pdfFilePath)) {
				Presentation presentation = createPresentation(convertedRepositoryPath, data);
				context.put(ConverterContext.PRESENTATION, presentation);
			} else {
				throw new CommandFailedToExecuteExeption();
			}
		} else {
			throw new CommandFailedToExecuteExeption();
		}
		return false;
	}

	private Presentation createPresentation(String convertedRepositoryPath, UploadedPresentationData data) {
		Presentation presentation = new Presentation(data);
		presentation.setRepositoryPath(convertedRepositoryPath);
		presentation.setNoOfSlides(34);
		presentation.setNoOfViews(0L);
		presentation.setSlideDuration(5L);
		return presentation;
	}

}
