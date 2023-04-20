package com.carparking.core_entity;

import com.carparking.core_auth.model.Role;

import static com.carparking.core_entity.RoleE.Value.*;

public enum RoleE implements Role {
	Officer(ROLE_OFFICER),
	Admin(ROLE_ADMIN),
	SystemAdmin(ROLE_SYSTEM_ADMIN);

	private String value;

	RoleE(String value) {
		this.value = value;
	}

	@Override
	public String getRoleCode() {
		return this.value;
	}

	public interface Value {
		String ROLE_OFFICER = "Officer";
		String ROLE_ADMIN = "Admin";
		String ROLE_SYSTEM_ADMIN = "SystemAdmin";
	}
}
