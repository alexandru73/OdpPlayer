package com.school.upload.job;

import java.io.Serializable;

public class Job implements Serializable {

	private static final long serialVersionUID = -4310027570895278111L;

	private Long uploadFileId;

	public Job(Long uploadFileId) {
		this.uploadFileId = uploadFileId;
	}

	public Long getUploadFileId() {
		return uploadFileId;
	}

	public void setUploadFileId(Long uploadFileId) {
		this.uploadFileId = uploadFileId;
	}
}
