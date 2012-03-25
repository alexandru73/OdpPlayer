package com.school.config;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;

public class ConfigurationLoader {
	private static CompositeConfiguration config = null;

	private static void init() {
		config = new CompositeConfiguration();
		config.addConfiguration(new SystemConfiguration());
		try {
			for (int i = 0; i < PROPERTIES_FILES.length; i++) {
				config.addConfiguration(new PropertiesConfiguration(PROPERTIES_FILES[i]));
			}
		} catch (ConfigurationException e) {
			// TODO log this
			e.printStackTrace();
		}
	}

	public static CompositeConfiguration getConfig() {
		if (config == null) {
			init();
		}
		return config;
	}

	private static final String[] PROPERTIES_FILES = { "config/repo.properties" };
}
