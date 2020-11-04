package com.atlas.scheduler.gateway.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class AuthenticationResponse implements Serializable {
	private static final long serialVersionUID = -9065590573018627609L;
	private String jwt;
}
