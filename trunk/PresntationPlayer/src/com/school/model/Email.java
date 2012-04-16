package com.school.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "email")
public class Email extends BaseEntity {
	private String subject;
	@Column(name = "email_address")
	private String emailAddress;
	@Column(columnDefinition = "TEXT")
	private String content;

	public Email() {
		super();
	}

	public Email(String subject, String emailAddress, String content) {
		super();
		this.subject = subject;
		this.emailAddress = emailAddress;
		this.content = content;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Email [subject=" + subject + ", emailAddress=" + emailAddress + ", content=" + content + "]";
	}

}
