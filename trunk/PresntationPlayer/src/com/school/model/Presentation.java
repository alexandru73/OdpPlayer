package com.school.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "presentation")
public class Presentation extends UploadedPresentationData {

	@Column(name = "no_of_slides", nullable = false)
	private Integer noOfSlides;
	@Column(name = "no_of_views", nullable = false)
	private Long noOfViews;
	@Column(name = "slide_duration", nullable = false)
	private Long slideDuration;

	public Long getSlideDuration() {
		return slideDuration;
	}

	public void setSlideDuration(Long slideDuration) {
		this.slideDuration = slideDuration;
	}

	public Integer getNoOfSlides() {
		return noOfSlides;
	}

	public void setNoOfSlides(Integer noOfSlides) {
		this.noOfSlides = noOfSlides;
	}

	public Long getNoOfViews() {
		return noOfViews;
	}

	public void setNoOfViews(Long noOfViews) {
		this.noOfViews = noOfViews;
	}

}
