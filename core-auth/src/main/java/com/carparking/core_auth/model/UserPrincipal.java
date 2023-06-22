
package com.carparking.core_auth.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserPrincipal implements UserDetails {

	@EqualsAndHashCode.Include
	private final Long id;

	private final String username;

	private final String password;

	private final boolean active;

//	private final Map<String, Object> additionalData;

	private final Collection<? extends GrantedAuthority> authorities;

	public UserPrincipal(
			Long id,
			String username,
			String password,
			boolean activate,
//			Map<String, Object> additionalData,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.active = activate;
//		this.additionalData = additionalData;
		this.authorities = authorities;
	}

	public static UserPrincipal create(User user) {
		List<? extends Role> roles = user.getRoles();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roles) {
			authorities.add(
					new SimpleGrantedAuthority(role.getRoleCode().toUpperCase())
			);
		}

		return new UserPrincipal(
				user.getId(),
				user.getUsername(),
				user.getPassword(),
				!user.isDelete(),
//				user.getAdditionalData(),
				authorities
		);
	}

	public Long getId() {
		return id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}

//	public Map<String, Object> getAdditionalData() {
//		return additionalData;
//	}
}
