package com.school.job.consumers;

import com.school.dao.BaseDao;
import com.school.job.Job;
import com.school.job.JobConsumer;

public class DeletePresentationJobConsumer extends JobConsumer {
	BaseDao baseDao;

	@Override
	public void execute(Job job) {
		// TODO Auto-generated method stub

	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

}
