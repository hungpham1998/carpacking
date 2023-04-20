package com.carparking.core_entity.entities;

import com.carparking.core_auth.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "account")
public class Account implements User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 50)
	@Column(name = "username")
	private String username;

	@Size(max = 120)
	@Column(name = "password")
	private String password;

	@Size(max = 50)
	@Email
	@Column(name = "email")
	private String email;

	@Column(name = "fullname")
	private String fullName;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "account_roles", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<RoleModel> roles;



	@Override
	public boolean isDelete() {
		return false;
	}

	@Override
	public Map<String, Object> getAdditionalData() {
		return null;
	}
}
