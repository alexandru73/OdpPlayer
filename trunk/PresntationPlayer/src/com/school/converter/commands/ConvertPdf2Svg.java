package com.school.converter.commands;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public class ConvertPdf2Svg implements Command{

	@Override
	public boolean execute(Context arg0) throws Exception {
		System.out.println("converting pdf 3 svg");
		Thread.sleep(150);
		return false;
	}

}
