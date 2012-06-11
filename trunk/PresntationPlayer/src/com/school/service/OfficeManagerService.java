package com.school.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeConnectionProtocol;
import org.artofsolving.jodconverter.office.OfficeManager;

import com.school.util.ConfigurationLoader;

public class OfficeManagerService implements ServletContextListener {
	public static OfficeManager officeManager = null;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if (officeManager != null) {
		//	officeManager.stop();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//officeManager = createAndStartOfficeManager(); 
	}

	public OfficeManager createAndStartOfficeManager() {
		int[] ports = initPorts();
		DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
		configuration.setConnectionProtocol(OfficeConnectionProtocol.SOCKET);
		configuration.setPortNumbers(ports[0], ports[1], ports[2], ports[3], ports[4]);
		OfficeManager officeManager = configuration.buildOfficeManager();
		officeManager.start();
		return officeManager;
	}

	public int[] initPorts() {
		if (ConfigurationLoader.getConfig() != null) {
			System.out.println(" ## from file");
			int[] ports = { ConfigurationLoader.getConfig().getInt(PORT_FIRST),
					ConfigurationLoader.getConfig().getInt(PORT_SECOND),
					ConfigurationLoader.getConfig().getInt(PORT_THIRD),
					ConfigurationLoader.getConfig().getInt(PORT_FOURTH),
					ConfigurationLoader.getConfig().getInt(PORT_FIFTH) };
			return ports;
		} else {
			int[] ports = { 8010, 8011, 8012, 8013, 8014 };
			return ports;
		}
	}

	public void stopOfficeManager(OfficeManager officeManager) {
		if (officeManager != null) {
			officeManager.stop();
		}
	}

	private static final String PORT_FIRST = "open.office.port.first", PORT_SECOND = "open.office.port.second",
			PORT_THIRD = "open.office.port.third", PORT_FOURTH = "open.office.port.fourth",
			PORT_FIFTH = "open.office.port.fifth", OFFICE_HOME = "open.office.home";
}
