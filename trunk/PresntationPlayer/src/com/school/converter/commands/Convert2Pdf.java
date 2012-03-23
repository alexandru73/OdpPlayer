package com.school.converter.commands;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public class Convert2Pdf implements Command{
	private static AtomicInteger portNumber=new AtomicInteger(8000);
	@Override
	public boolean execute(Context arg0) throws Exception {
		System.err.println(" port no for :"+portNumber.getAndIncrement());
		Thread.sleep(60);
		System.err.println(" port no for :"+portNumber.getAndDecrement());
		System.out.println("converting 2 pdf");
		return false;
	}

}
