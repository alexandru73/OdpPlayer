package com.school.presentation.converter.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

import com.school.exceptions.CommandFailedToExecuteExeption;
import com.school.model.Bookmark;
import com.school.model.Presentation;
import com.school.model.UploadedPresentationData;
import com.school.presentation.converter.impl.ConverterContext;

public class ExtractMetadata implements Command {

	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Context context) throws Exception {
		if (context.containsKey(ConverterContext.UPLOADED_DATA)
				&& context.containsKey(ConverterContext.PDF_CONVERSION_URL)) {

			String convertedRepositoryPath = (String) context.get(ConverterContext.REPO_CONVERTED);
			String pdfFilePath = (String) context.get(ConverterContext.PDF_CONVERSION_URL);
			UploadedPresentationData data = (UploadedPresentationData) context.get(ConverterContext.UPLOADED_DATA);
			if (data != null && StringUtils.isNotEmpty(convertedRepositoryPath) && StringUtils.isNotEmpty(pdfFilePath)) {
				Presentation presentation = createPresentation(convertedRepositoryPath, data, pdfFilePath);
				context.put(ConverterContext.PRESENTATION, presentation);
			} else {
				throw new CommandFailedToExecuteExeption();
			}
		} else {
			throw new CommandFailedToExecuteExeption();
		}
		return false;
	}

	private Presentation createPresentation(String convertedRepositoryPath, UploadedPresentationData data,
			String pdfFilePath) throws IOException {
		PDDocument doc = PDDocument.load(new File(pdfFilePath));
		List<Bookmark> bookmarks = getBookmarks(doc);

		Presentation presentation = new Presentation(data);
		presentation.setBookmarks(bookmarks);
		presentation.setRepositoryPath(convertedRepositoryPath);
		presentation.setNoOfSlides(doc.getNumberOfPages());
		presentation.setBookmarks(bookmarks);
		return presentation;
	}

	private static List<Bookmark> getBookmarks(PDDocument doc) {
		PDDocumentOutline root = doc.getDocumentCatalog().getDocumentOutline();
		PDOutlineItem item = root.getFirstChild();
		List<Bookmark> bookmarks = new ArrayList<>();
		int pageNo = 1;
		while (item != null) {
			Bookmark bookmark = new Bookmark(pageNo, item.getTitle());
			bookmarks.add(bookmark);
			pageNo++;
			item = item.getNextSibling();
		}
		return bookmarks;
	}
}
