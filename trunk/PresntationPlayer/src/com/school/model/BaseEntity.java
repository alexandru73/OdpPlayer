package com.school.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

@MappedSuperclass
public abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	@Column(name = "created_at", nullable = false)
	@Type(type = "timestamp")
	private Date createadAt;

	public Date getCreateadAt() {
		return createadAt;
	}

	public void setCreateadAt(Date createadAt) {
		this.createadAt = createadAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
