package com.school.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "bookmark")
@XmlRootElement
public class Bookmark extends BaseEntity {
	@Column(name = "slide_no", nullable = false)
	private int slideNo;
	private String comment;
	@ManyToOne(fetch = FetchType.LAZY)
	private Presentation presention;

	public Bookmark(int slideNo, String comment) {
		this.slideNo = slideNo;
		this.comment = comment;
	}

	public Presentation getPresention() {
		return presention;
	}

	public void setPresention(Presentation presention) {
		this.presention = presention;
	}

	public int getSlideNo() {
		return slideNo;
	}

	public void setSlideNo(int slideNo) {
		this.slideNo = slideNo;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	//
}
