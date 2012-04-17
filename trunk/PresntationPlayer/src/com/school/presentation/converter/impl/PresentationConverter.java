package com.school.presentation.converter.impl;

import org.apache.commons.chain.Context;

import com.school.exceptions.CommandNotFoundException;
import com.school.exceptions.CommandRollbackFailedException;
import com.school.presentation.converter.Converter;
import com.school.run.CommandRunner;

public class PresentationConverter implements Converter {
	CommandRunner commandRunner;

	public PresentationConverter(CommandRunner commandRunner) {
		this.commandRunner = commandRunner;
	}

	@Override
	public boolean convert(Context converterContext) {
		try {
			commandRunner.executeCommand(CONVERT_PRESENTATION_COMMAND, CONVERT_PRESENTATION_ROLLBACK, converterContext);
		} catch (CommandNotFoundException | CommandRollbackFailedException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static final String CONVERT_PRESENTATION_COMMAND = "presentationConverterChain",
			CONVERT_PRESENTATION_ROLLBACK = "presentationConverterRollback";

}
