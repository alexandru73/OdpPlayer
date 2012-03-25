package com.school.converter;

import org.apache.commons.chain.Context;

import com.school.exceptions.CommandFailedToExecuteExeption;
import com.school.exceptions.CommandNotFoundException;

public interface CommandRunner {
	public void runCommand(String commandName, Context commandContext)
			throws CommandNotFoundException, CommandFailedToExecuteExeption;
}
