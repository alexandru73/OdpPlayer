package com.school.converter.commands;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public class Convert2Pdf implements Command{

	@Override
	public boolean execute(Context arg0) throws Exception {
		System.out.println("converting 2 pdf");
		return false;
	}

}
