package com.school.converter.commands;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public class SendNotification implements Command{

	@Override
	public boolean execute(Context arg0) throws Exception {
		System.out.println("trimit email");
		return true;
	}

}
