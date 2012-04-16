package com.school.presentation.converter.commands;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import com.school.dao.BaseDao;
import com.school.exceptions.CommandFailedToExecuteExeption;
import com.school.job.JobSenderImpl;
import com.school.model.Presentation;
import com.school.model.UploadedPresentationData;
import com.school.presentation.converter.impl.ConverterContext;

public class SaveConversionData implements Command {
	private BaseDao baseDao;
	JobSenderImpl queue;

	@Override
	public boolean execute(Context context) throws Exception {
		System.out.println("save conversion data");
		if (!context.containsKey(ConverterContext.PRESENTATION) || !context.containsKey(ConverterContext.UPLOADED_DATA)) {
			throw new CommandFailedToExecuteExeption();
		}
		Presentation presentation = (Presentation) context.get(ConverterContext.PRESENTATION);
		UploadedPresentationData uploadData = (UploadedPresentationData) context.get(ConverterContext.UPLOADED_DATA);
		if (presentation == null || uploadData == null) {
			throw new CommandFailedToExecuteExeption();
		}
		makeDbChanges(presentation, uploadData);
		return false;
	}

	private void makeDbChanges(Presentation presentation, UploadedPresentationData uploadData) {
		Long id = baseDao.save(presentation);
		presentation.setId(id);
		baseDao.delete(uploadData);
		uploadData.setId(null);
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void setQueue(JobSenderImpl queue) {
		this.queue = queue;
	}
}
