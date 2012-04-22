package com.school.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="cathegory")
public class Cathegory extends BaseEntity {

	private static final long serialVersionUID = 6121994437070495406L;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
