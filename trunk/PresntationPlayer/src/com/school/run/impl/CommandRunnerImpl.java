package com.school.run.impl;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.school.presentation.exceptions.CommandFailedToExecuteExeption;
import com.school.presentation.exceptions.CommandNotFoundException;
import com.school.presentation.exceptions.CommandRollbackFailedException;
import com.school.run.CommandRunner;

public class CommandRunnerImpl extends CommandRunner implements ApplicationContextAware {
	private ApplicationContext appContext;

	@Override
	public void runCommand(String commandName, Context commandContext) throws CommandNotFoundException,
			CommandFailedToExecuteExeption {
		try {
			Command command = createCommandByName(commandName);
			command.execute(commandContext);
		} catch (BeansException e) {
			throw new CommandNotFoundException(e);
		} catch (Exception e) {
			throw new CommandFailedToExecuteExeption(e);
		}
	}

	@Override
	protected void runRollback(String commandName, Context commandContext) throws CommandNotFoundException,
			CommandRollbackFailedException {
		try {
			Command command = createCommandByName(commandName);
			command.execute(commandContext);
		} catch (BeansException e) {
			throw new CommandNotFoundException(e);
		} catch (Exception e) {
			throw new CommandRollbackFailedException();
		}

	}

	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		this.appContext = appContext;
	}

	private synchronized Command createCommandByName(String commandName) {
		return (Command) appContext.getBean(commandName);
	}

}
