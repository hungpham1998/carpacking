package com.carparking.application.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RoleRequest {
	@NotNull(message = "cannot be null")
	@NotEmpty(message = "cannot be empty")
	private String name;

	private Long id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
