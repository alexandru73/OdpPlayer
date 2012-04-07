package com.school.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "uploaded_presentation_data")
public class UploadedPresentationData extends BaseEntity {

	@Column(name = "title", nullable = false)
	private String title;
	@Column(name = "description", nullable = false)
	private String description;
	@Column(name = "cathegory", nullable = false)
	private String cathegory;
	@Column(name = "repository_name", nullable = false)
	private String repositoryName;
	@Column(name = "repository_path", nullable = false)
	private String repositoryPath;
	@ManyToOne(targetEntity = User.class)
	private User user;

	public boolean isValidData() {
		boolean ok = true;
		if (StringUtils.isBlank(title) || StringUtils.isBlank(description) || StringUtils.isBlank(cathegory)) {
			ok = false;
		}
		return ok;
	}

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
