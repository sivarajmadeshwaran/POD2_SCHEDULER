package com.atlas.scheduler.gateway.model;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto implements Serializable {

	private static final long serialVersionUID = 2154880638411800912L;
	
	private String firstName;
	private String lastName;
	private String password;
	private String userId;
	private Set<String> role;
}