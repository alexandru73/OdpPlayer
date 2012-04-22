package com.school.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.annotations.Type;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	@Column(name = "created_at", nullable = false)
	@Type(type = "timestamp")
	private Date createadAt;
	
	public BaseEntity() {
		createadAt=new Date();
	}
	
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
