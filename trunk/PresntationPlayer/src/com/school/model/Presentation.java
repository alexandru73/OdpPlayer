package com.school.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "presentation")
public class Presentation extends UploadedPresentationData {

	@Column(name = "no_of_slides", nullable = false)
	private Integer noOfSlides;
	@Column(name = "extension", nullable = false)
	private String extension;
	@Column(name = "no_of_views", nullable = false)
	private Long noOfViews;

	public Integer getNoOfSlides() {
		return noOfSlides;
	}

	public void setNoOfSlides(Integer noOfSlides) {
		this.noOfSlides = noOfSlides;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Long getNoOfViews() {
		return noOfViews;
	}

	public void setNoOfViews(Long noOfViews) {
		this.noOfViews = noOfViews;
	}

}
