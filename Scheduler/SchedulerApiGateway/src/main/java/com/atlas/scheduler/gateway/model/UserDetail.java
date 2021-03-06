package com.atlas.scheduler.gateway.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UserDetail extends User {

	private static final long serialVersionUID = 808791015103274795L;

	private String roles;

	public UserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
}
