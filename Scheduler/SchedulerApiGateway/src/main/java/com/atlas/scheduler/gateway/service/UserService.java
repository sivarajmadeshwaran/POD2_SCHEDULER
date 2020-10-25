package com.atlas.scheduler.gateway.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.atlas.scheduler.gateway.exceptions.InvalidCredentialsException;
import com.atlas.scheduler.gateway.model.UserDto;

public interface UserService extends UserDetailsService {

	UserDto createUser(UserDto userReq);
	
	String authenticate(UserDto userReq) throws InvalidCredentialsException;

}
