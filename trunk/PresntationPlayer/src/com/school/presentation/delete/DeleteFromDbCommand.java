package com.school.presentation.delete;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import com.school.dao.BaseDao;
import com.school.exceptions.CommandFailedToExecuteExeption;
import com.school.model.DetailedPresentation;
import com.school.presentation.converter.impl.ConverterContext;

public class DeleteFromDbCommand implements Command {
	BaseDao baseDao;

	@Override
	public boolean execute(Context context) throws Exception {
		if (!context.containsKey(ConverterContext.DETAILED_PRESENTATION)) {
			throw new CommandFailedToExecuteExeption();
		}
		DetailedPresentation presentation = (DetailedPresentation) context.get(ConverterContext.DETAILED_PRESENTATION);
		baseDao.delete(presentation);
		presentation.setId(null);
		return false;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

}
