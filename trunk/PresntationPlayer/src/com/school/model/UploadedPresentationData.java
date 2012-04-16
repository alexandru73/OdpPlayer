package com.school.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "uploaded_presentation_data")
@XmlRootElement
public class UploadedPresentationData extends BaseEntity {

	@Column(name = "title", nullable = false)
	protected String title;
	@Column(name = "description", nullable = false)
	protected String description;
	@Column(name = "cathegory", nullable = false)
	protected String cathegory;
	@Column(name = "repository_name", nullable = false)
	protected String repositoryName;
	@Column(name = "repository_path", nullable = false)
	protected String repositoryPath;
	@Column(name = "original_extension", nullable = false)
	protected String originalExtension;
	@Column(name = "slide_duration", nullable = false)
	protected Long slideDuration;
	@ManyToOne(targetEntity = User.class)
	protected User user;

	public UploadedPresentationData() {
		// TODO Auto-generated constructor stub
	}

	public UploadedPresentationData(String title, String description, String cathegory) {
		this.title = title;
		this.description = description;
		this.cathegory = cathegory;
	}

	public boolean isValidData() {
		boolean ok = true;
		if (StringUtils.isBlank(title) || StringUtils.isBlank(description) || StringUtils.isBlank(cathegory)) {
			ok = false;
		}
		return ok;
	}

	public Long getSlideDuration() {
		return slideDuration;
	}

	public void setSlideDuration(Long slideDuration) {
		this.slideDuration = slideDuration;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOriginalExtension() {
		return originalExtension;
	}

	public void setOriginalExtension(String originalExtension) {
		this.originalExtension = originalExtension;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCathegory() {
		return cathegory;
	}

	public void setCathegory(String cathegory) {
		this.cathegory = cathegory;
	}

	public String getRepositoryName() {
		return repositoryName;
	}

	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}

	public String getRepositoryPath() {
		return repositoryPath;
	}

	public void setRepositoryPath(String repositoryPath) {
		this.repositoryPath = repositoryPath;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UploadedPresentationData [title=" + title + ", description=" + description + ", cathegory=" + cathegory
				+ ", repositoryName=" + repositoryName + ", repositoryPath=" + repositoryPath + ", user=" + user + "]";
	}

}
