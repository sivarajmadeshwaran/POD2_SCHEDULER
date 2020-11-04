package com.atlas.scheduler.gateway.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.atlas.scheduler.gateway.configuration.JWTUtils;
import com.atlas.scheduler.gateway.exceptions.InvalidCredentialsException;
import com.atlas.scheduler.gateway.model.UserDetail;
import com.atlas.scheduler.gateway.model.UserDto;
import com.atlas.scheduler.gateway.repository.ERole;
import com.atlas.scheduler.gateway.repository.RoleRepository;
import com.atlas.scheduler.gateway.repository.Roles;
import com.atlas.scheduler.gateway.repository.UserEntity;
import com.atlas.scheduler.gateway.repository.UserRepository;

@TestMethodOrder(OrderAnnotation.class)
class UserServiceImplTest {

	@Mock
	UserRepository userRepo;

	@Mock
	BCryptPasswordEncoder encoder;

	@Mock
	RoleRepository roleRepo;

	@Mock
	AuthenticationManager authenticationManager;

	@Mock
	JWTUtils jwtUtils;

	UserServiceImpl userService = null;
	UserDto user = null;
	UserEntity userEntity = null;
	UserDetail userDetail = null;

	@BeforeEach
	public void setUp() {

		MockitoAnnotations.initMocks(this);

		userService = new UserServiceImpl();
		userService.setUserRepo(userRepo);
		userService.setEncoder(encoder);
		userService.setRoleRepository(roleRepo);
		userService.setAuthenticationManager(authenticationManager);
		userService.setJwtUtils(jwtUtils);

		user = new UserDto();
		user.setFirstName("Siva");
		user.setLastName("Raj");
		user.setPassword("12345678");
		user.setUserId("f2c82456-0c12-4c6a-8a6b-a1aa59f9bcf5");
		Set<String> rols = new HashSet<>();
		rols.add("admin");
		rols.add("user");
		user.setRole(rols);

		userEntity = new UserEntity();
		userEntity.setFirstName("Siva");
		userEntity.setLastName("Raj");
		userEntity.setUserId("f2c82456-0c12-4c6a-8a6b-a1aa59f9bcf5");
		userEntity.setEncryptedPassword("$2a$10$E3o0Pow9hXKH2alA9Qg0k.91gjoDxN4EpOThgebqQe7VzvFUJ1mu.");
		Roles roles = new Roles(1, ERole.ADMIN);
		Set<Roles> rolesEntitySet = new HashSet<>();
		rolesEntitySet.add(roles);
		userEntity.setRoles(rolesEntitySet);
	}

	@DisplayName("Create User")
	@Test
	@Order(1)
	void testCreateUser() {
		when(userRepo.save(userEntity)).thenReturn(userEntity);
		userService.createUser(user);
		when(userRepo.findByUserId("f2c82456-0c12-4c6a-8a6b-a1aa59f9bcf5")).thenReturn(userEntity);
		assertTrue(userEntity.getLastName().equalsIgnoreCase("Raj"));
	}

	@DisplayName("loadUser By Username")
	@Test
	@Order(2)
	void loadUserByUsername() {
		when(userRepo.findByUserId("f2c82456-0c12-4c6a-8a6b-a1aa59f9bcf5")).thenReturn(userEntity);
		UserDetails userDetails = userService.loadUserByUsername("f2c82456-0c12-4c6a-8a6b-a1aa59f9bcf5");
		verify(userRepo).findByUserId("f2c82456-0c12-4c6a-8a6b-a1aa59f9bcf5");
		assertEquals("f2c82456-0c12-4c6a-8a6b-a1aa59f9bcf5", userDetails.getUsername());
	}

	@DisplayName("Authenticate  User")
	@Test
	@Order(3)
	void authenticateUser() throws InvalidCredentialsException {
		Authentication auth = new UsernamePasswordAuthenticationToken("f2c82456-0c12-4c6a-8a6b-a1aa59f9bcf5",
				"$2a$10$E3o0Pow9hXKH2alA9Qg0k.91gjoDxN4EpOThgebqQe7VzvFUJ1mu.");
		when(authenticationManager.authenticate(isA(Authentication.class))).thenReturn(auth);
		when(userRepo.findByUserId("f2c82456-0c12-4c6a-8a6b-a1aa59f9bcf5")).thenReturn(userEntity);
		when(jwtUtils.generateToken(isA(UserDetail.class))).thenReturn("efbsefbebfsebfe");
		String token = userService.authenticate(user);
		verify(authenticationManager).authenticate(isA(Authentication.class));
		verify(userRepo).findByUserId("f2c82456-0c12-4c6a-8a6b-a1aa59f9bcf5");
		verify(jwtUtils).generateToken(isA(UserDetail.class));
		assertEquals("efbsefbebfsebfe", token);
	}

	@DisplayName("User Not Found  Failure Scenario")
	@Test
	@Order(4)
	void userNotFoundScenario() throws InvalidCredentialsException {
		when(authenticationManager.authenticate(isA(Authentication.class))).thenReturn(isA(Authentication.class));
		when(userRepo.findByUserId("f2c82456-0c12-4c6a-8a6b-a1aa59f9bcf5")).thenReturn(userEntity);
		when(jwtUtils.generateToken(isA(UserDetail.class))).thenReturn("efbsefbebfsebfe");
		Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.authenticate(user));
	}

	@DisplayName("Invalid Credentials Scenario")
	@Test
	@Order(5)
	void invalidCredentialsScenario() throws InvalidCredentialsException {
		when(authenticationManager.authenticate(isA(Authentication.class))).thenThrow(BadCredentialsException.class);
		when(userRepo.findByUserId("f2c82456-0c12-4c6a-8a6b-a1aa59f9bcf5")).thenReturn(userEntity);
		when(jwtUtils.generateToken(isA(UserDetail.class))).thenReturn("efbsefbebfsebfe");
		Assertions.assertThrows(InvalidCredentialsException.class, () -> userService.authenticate(user));
	}

	@DisplayName("Create User without roles")
	@Test
	@Order(6)
	void testCreateUserWithOutRoles() {
		user.setRole(null);
		when(userRepo.save(userEntity)).thenReturn(userEntity);
		userService.createUser(user);
		when(userRepo.findByUserId("f2c82456-0c12-4c6a-8a6b-a1aa59f9bcf5")).thenReturn(userEntity);
		assertTrue(userEntity.getLastName().equalsIgnoreCase("Raj"));
	}

}
