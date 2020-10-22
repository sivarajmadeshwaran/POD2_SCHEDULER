package com.atlas.scheduler.gateway.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.atlas.scheduler.gateway.exceptions.InvalidCredentialsException;
import com.atlas.scheduler.gateway.model.AuthenticationResponse;
import com.atlas.scheduler.gateway.model.UserDto;
import com.atlas.scheduler.gateway.service.UserService;

@RestController()
public class UserController {

	@Autowired
	UserService userService;


	@PostMapping()
	public ResponseEntity<UserDto> createuser(@RequestBody UserDto userReq) {
		return new ResponseEntity<UserDto>(userService.createUser(userReq), HttpStatus.CREATED);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody UserDto userReq) throws InvalidCredentialsException {
		String jwtToken=userService.authenticate(userReq);
		return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
	}

}
