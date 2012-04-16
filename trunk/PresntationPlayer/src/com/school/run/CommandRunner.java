package com.school.run;

import org.apache.commons.chain.Context;

import com.school.exceptions.CommandFailedToExecuteExeption;
import com.school.exceptions.CommandNotFoundException;
import com.school.exceptions.CommandRollbackFailedException;

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
			e.printStackTrace();
			runRollback(rollbackCommandName, commandContext);
		}
	}
}
