package com.school.job;

import java.io.Serializable;

public class Job implements Serializable {

	private static final long serialVersionUID = -4310027570895278111L;

	private Long jobId;

	public Job(Long jobId) {
		this.jobId = jobId;
	}

	public Long getjobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

}
