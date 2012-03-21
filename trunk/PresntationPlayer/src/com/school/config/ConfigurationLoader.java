package com.school.config;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;

public class ConfigurationLoader {
	private CompositeConfiguration config = new CompositeConfiguration();

	public ConfigurationLoader() {
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

	public CompositeConfiguration getConfig() {
		return config;
	}

	private static final String[] PROPERTIES_FILES = { "config/repo.properties" };
}
