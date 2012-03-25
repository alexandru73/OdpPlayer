package com.school.converter;

import org.apache.commons.chain.impl.ContextBase;

public class ConverterContext extends ContextBase {

	private static final long serialVersionUID = 696359068777091633L;

	private int portNumber;

	public int getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}
	
}
