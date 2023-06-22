package com.carparking.core_entity.entities;


import com.carparking.core_auth.model.Role;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "role")
public class RoleModel implements Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@Enumerated(EnumType.STRING)
	@Column(name = "name", length = 20)
	private String name;

	/**
	 * @return
	 */
	@Column(name = "RoleCode")
	private String roleCode;
}
