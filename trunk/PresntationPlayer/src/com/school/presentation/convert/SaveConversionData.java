package com.school.presentation.convert;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import com.school.dao.BaseDao;
import com.school.job.sender.IJobSender;
import com.school.model.DetailedPresentation;
import com.school.model.Presentation;
import com.school.presentation.exceptions.CommandFailedToExecuteExeption;

public class SaveConversionData implements Command {
	private BaseDao baseDao;
	IJobSender queue;

	@Override
	public boolean execute(Context context) throws Exception {
		System.out.println("save conversion data");
		if (!context.containsKey(ConverterContext.DETAILED_PRESENTATION) || !context.containsKey(ConverterContext.PRESENTATION)) {
			throw new CommandFailedToExecuteExeption();
		}
		DetailedPresentation presentation = (DetailedPresentation) context.get(ConverterContext.DETAILED_PRESENTATION);
		Presentation uploadData = (Presentation) context.get(ConverterContext.PRESENTATION);
		if (presentation == null || uploadData == null) {
			throw new CommandFailedToExecuteExeption();
		}
		makeDbChanges(presentation, uploadData);
		return false;
	}

	private void makeDbChanges(DetailedPresentation presentation, Presentation uploadData) {
		Long id = baseDao.save(presentation);
		presentation.setId(id);
		baseDao.delete(uploadData);
		uploadData.setId(null);
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void setQueue(IJobSender queue) {
		this.queue = queue;
	}
}
