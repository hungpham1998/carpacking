package com.carparking.application.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LoginRequest {

	@NotNull(message = "cannot be null")
	@NotEmpty(message = "cannot be empty")
	private String username;

	@NotNull(message = "cannot be null")
	@NotEmpty(message = "cannot be empty")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
