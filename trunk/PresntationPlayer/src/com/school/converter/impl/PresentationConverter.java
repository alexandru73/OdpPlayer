package com.school.converter.impl;

import com.school.converter.CommandRunner;
import com.school.converter.Converter;
import com.school.converter.ConverterContext;
import com.school.exceptions.CommandFailedToExecuteExeption;
import com.school.exceptions.CommandNotFoundException;

public class PresentationConverter implements Converter {
	CommandRunner commandRunner;	
	
	public PresentationConverter(CommandRunner commandRunner) {
		this.commandRunner = commandRunner;
	}

	@Override
	public boolean convert(ConverterContext converterContext) {
		try {
			commandRunner.runCommand(CONVERT_PRESENTATION_COMMAND, converterContext);
		} catch (CommandNotFoundException | CommandFailedToExecuteExeption e) {
			// TODO log this
			e.printStackTrace();
		}
		return false;
	}


	private static final String CONVERT_PRESENTATION_COMMAND = "presentationConverterChain";

}
