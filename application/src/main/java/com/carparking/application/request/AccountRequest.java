package com.carparking.application.request;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AccountRequest {
	private Long id;

	@NotBlank
	@NotNull(message = "cannot be null")
	@NotEmpty(message = "cannot be empty")
	@Size(max = 120)
	private String username;

	@NotBlank(message = "cannot")
	@NotNull(message = "cannot be null")
	@NotNull(message = "cannot be null")
	@Size(max = 120)
	private String password;

	@NotBlank
	@NotNull(message = "cannot be null")
	@NotEmpty(message = "cannot be empty")
	@Email
	private String email;

	private List<Long> listRole = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Long> getListRole() {
		return listRole;
	}

	public void setListRole(List<Long> listRole) {
		this.listRole = listRole;
	}

}
