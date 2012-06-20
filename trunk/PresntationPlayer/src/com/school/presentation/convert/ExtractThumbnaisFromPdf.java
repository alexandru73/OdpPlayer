package com.school.presentation.convert;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdfviewer.PageDrawer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import com.school.model.Presentation;
import com.school.presentation.exceptions.CommandFailedToExecuteExeption;
import com.school.presentation.exceptions.SvgConversionFailedException;
import com.school.util.OtherUtils;

public class ExtractThumbnaisFromPdf implements Command {

	@Override
	public boolean execute(Context context) throws Exception {
		if (context.containsKey(ConverterContext.PRESENTATION)
				&& context.containsKey(ConverterContext.PDF_CONVERSION_URL)) {

			String convertedRepositoryPath = (String) context.get(ConverterContext.REPO_CONVERTED);
			String pdfFilePath = (String) context.get(ConverterContext.PDF_CONVERSION_URL);
			Presentation data = (Presentation) context.get(ConverterContext.PRESENTATION);
			if (data != null && StringUtils.isNotEmpty(convertedRepositoryPath) && StringUtils.isNotEmpty(pdfFilePath)) {
				String folder = createThumbnailsFolder(context, data);
				extractThumbnails(data, pdfFilePath, folder);
			} else {
				throw new CommandFailedToExecuteExeption();
			}
		} else {
			throw new CommandFailedToExecuteExeption();
		}
		return false;
	}

	public void extractThumbnails(Presentation data, String pdfFilePath, String folder) throws IOException,
			CommandFailedToExecuteExeption {
		String commandLine = "gs -dBATCH  -dNOPAUSE  -sDEVICE=png16m -r20x20 -sOutputFile=" + folder + "/"
				+ data.getRepositoryName() + "%d.png " + pdfFilePath;
		Process svgConverterProcess = Runtime.getRuntime().exec(commandLine);
		int exitCode;
		try {
			exitCode = svgConverterProcess.waitFor();
			if (exitCode != 0) {
				throw new CommandFailedToExecuteExeption();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private String createThumbnailsFolder(Context context, Presentation data) throws CommandFailedToExecuteExeption {
		String repoHome = (String) context.get(ConverterContext.REPO_HOME);
		String convertedRepositoryPath = (String) context.get(ConverterContext.REPO_CONVERTED);
		if (data != null && StringUtils.isNotEmpty(repoHome) && StringUtils.isNotEmpty(convertedRepositoryPath)) {
			String imgFolderPath = OtherUtils.concatStringsAscending(repoHome, "/", convertedRepositoryPath, "/",
					data.getRepositoryName(), "/thumbnails");
			File imgDir = new File(imgFolderPath);
			imgDir.mkdir();
			return imgFolderPath;
		} else {
			throw new CommandFailedToExecuteExeption();
		}
	}
}
