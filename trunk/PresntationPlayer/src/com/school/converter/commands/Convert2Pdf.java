package com.school.converter.commands;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import com.school.converter.ooffice.OfficeManagerLoader;

public class Convert2Pdf implements Command {

	@Override
	public boolean execute(Context context) throws Exception {
		System.err.println("officeManager" + OfficeManagerLoader.officeManager);
		return false;
	}
}
