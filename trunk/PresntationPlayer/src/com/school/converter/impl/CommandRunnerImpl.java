package com.school.converter.impl;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.school.converter.CommandRunner;
import com.school.converter.ConverterContext;
import com.school.exceptions.CommandFailedToExecuteExeption;
import com.school.exceptions.CommandNotFoundException;

public class CommandRunnerImpl implements CommandRunner, ApplicationContextAware {
	private ApplicationContext appContext;

	@Override
	public void runCommand(String commandName, ConverterContext commandContext) throws CommandNotFoundException,
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
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		this.appContext = appContext;
	}

	private synchronized Command createCommandByName(String commandName) {
		return (Command) appContext.getBean(commandName);
	}

}
