package com.school.job;

import java.io.Serializable;

public class Job implements Serializable {

	private static final long serialVersionUID = -4310027570895278111L;

	private Long jobId;
	private JobType jobType;

	public Job(Long jobId) {
		this.jobId = jobId;
		this.jobType = null;
	}

	public Job(Long jobId, JobType jobType) {
		this.jobId = jobId;
		this.jobType = jobType;
	}

	public Long getjobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public JobType getJobType() {
		return jobType;
	}

	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}

	public enum JobType {
		DELETE, CONVERT, UPDATE;
	}
}
