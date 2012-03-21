package com.school.converter;

import com.school.exceptions.CommandFailedToExecuteExeption;
import com.school.exceptions.CommandNotFoundException;

public interface CommandRunner {
	public void runCommand(String commandName, ConverterContext commandContext)
			throws CommandNotFoundException, CommandFailedToExecuteExeption;
}
