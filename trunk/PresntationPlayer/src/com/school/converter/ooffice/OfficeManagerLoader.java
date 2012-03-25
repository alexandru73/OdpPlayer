package com.school.converter.ooffice;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeConnectionProtocol;
import org.artofsolving.jodconverter.office.OfficeManager;

public class OfficeManagerLoader implements ServletContextListener {

	public static OfficeManager officeManager = null;		

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		officeManager.stop();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		officeManager = createAndStartOfficeManager();
	}

	public OfficeManager createAndStartOfficeManager() {
		DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
		configuration.setConnectionProtocol(OfficeConnectionProtocol.SOCKET);
		configuration.setPortNumbers(8010, 8011, 8012, 8013, 8014);
		OfficeManager officeManager = configuration.buildOfficeManager();
		officeManager.start();
		return officeManager;
	}

	public void stopOfficeManager(OfficeManager officeManager) {
		if (officeManager != null) {
			officeManager.stop();
		}
	}

}
