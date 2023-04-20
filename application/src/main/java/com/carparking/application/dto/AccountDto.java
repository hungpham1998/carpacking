package com.carparking.application.dto;

import com.carparking.core_entity.entities.RoleModel;

import java.util.ArrayList;
import java.util.List;


public class AccountDto {
	private String id;
	private String userName;
	private String fullName;
	private String password;
	private List<RoleModel> roles = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<RoleModel> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleModel> roles) {
		this.roles = roles;
	}

}
