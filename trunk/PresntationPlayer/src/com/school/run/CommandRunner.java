package com.school.run;

import org.apache.commons.chain.Context;

import com.school.presentation.exceptions.CommandFailedToExecuteExeption;
import com.school.presentation.exceptions.CommandNotFoundException;
import com.school.presentation.exceptions.CommandRollbackFailedException;

public abstract class CommandRunner {

	protected abstract void runCommand(String commandName, Context commandContext) throws CommandNotFoundException,
			CommandFailedToExecuteExeption;

	protected abstract void runRollback(String commandName, Context commandContext) throws CommandNotFoundException,
			CommandRollbackFailedException;

	public void executeCommand(String commandName, String rollbackCommandName, Context commandContext)
			throws CommandNotFoundException, CommandRollbackFailedException {
		try {
			runCommand(commandName, commandContext);
		} catch (CommandFailedToExecuteExeption e) {
			runRollback(rollbackCommandName, commandContext);
		}
	}
}
