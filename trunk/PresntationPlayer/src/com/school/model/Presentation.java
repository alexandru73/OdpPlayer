package com.school.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "presentation")
@OnDelete(action = OnDeleteAction.CASCADE)
public class Presentation extends UploadedPresentationData {

	@Column(name = "no_of_slides", nullable = false)
	private Integer noOfSlides;
	@Column(name = "no_of_views", nullable = false)
	private Long noOfViews;

	@OneToMany(targetEntity = Bookmark.class, cascade = CascadeType.REMOVE)
	private List<Bookmark> bookmarks;

	public Presentation(UploadedPresentationData data) {
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

	public Presentation() {
	}

	public List<Bookmark> getBookmarks() {
		return bookmarks;
	}

	public void setBookmarks(List<Bookmark> bookmarks) {
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
