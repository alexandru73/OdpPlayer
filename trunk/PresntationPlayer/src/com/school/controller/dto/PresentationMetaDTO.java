package com.school.controller.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PresentationMetaDTO {
	protected String title;
	protected String description;
	protected Long cathegory;
	protected Long slideDuration;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCathegory() {
		return cathegory;
	}

	public void setCathegory(Long cathegory) {
		this.cathegory = cathegory;
	}

	public Long getSlideDuration() {
		return slideDuration;
	}

	public void setSlideDuration(Long slideDuration) {
		this.slideDuration = slideDuration;
	}

}
