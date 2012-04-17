package com.school.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "presentation")
public class DetailedPresentation extends Presentation {

	@Column(name = "no_of_slides", nullable = false)
	private Integer noOfSlides;
	@Column(name = "no_of_views", nullable = false)
	private Long noOfViews;

	@Column(columnDefinition="TEXT")
	private String bookmarks;

	public DetailedPresentation(Presentation data) {
		this.title = data.getTitle();
		this.description = data.getDescription();
		this.originalExtension = data.getOriginalExtension();
		this.cathegory = data.getCathegory();
		this.repositoryName = data.getRepositoryName();
		this.user = data.getUser();
		this.originalExtension = data.getOriginalExtension();
		this.slideDuration = data.getSlideDuration();
		this.noOfViews = 0L;
		setCreateadAt(data.getCreateadAt());
	}

	public DetailedPresentation() {
	}

	public String getBookmarks() {
		return bookmarks;
	}

	public void setBookmarks(String bookmarks) {
		this.bookmarks = bookmarks;
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
