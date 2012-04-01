package com.school.service;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class ResourceService implements ResourceLoaderAware {
	private ResourceLoader resourceLoader;

	public Resource getResource(String location) {
		return resourceLoader.getResource(location);
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;

	}
}
