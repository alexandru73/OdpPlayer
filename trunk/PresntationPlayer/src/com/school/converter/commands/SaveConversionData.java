package com.school.converter.commands;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import com.school.converter.impl.ConverterContext;
import com.school.dao.BaseDao;
import com.school.exceptions.CommandFailedToExecuteExeption;
import com.school.model.Presentation;
import com.school.model.UploadedPresentationData;

public class SaveConversionData implements Command {
	private BaseDao baseDao;

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
		baseDao.save(presentation);
		baseDao.delete(uploadData);
		return false;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

}
