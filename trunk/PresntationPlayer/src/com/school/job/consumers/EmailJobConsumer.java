package com.school.job.consumers;

import javax.jms.Message;
import javax.jms.MessageListener;

import com.school.dao.BaseDao;

public class EmailJobConsumer implements MessageListener {
	BaseDao baseDao;

	@Override
	public void onMessage(Message arg0) {
		// TODO Auto-generated method stub

	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

}
