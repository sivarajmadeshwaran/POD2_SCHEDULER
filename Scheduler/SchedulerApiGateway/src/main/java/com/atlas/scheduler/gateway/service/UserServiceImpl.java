package com.atlas.scheduler.gateway.service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.atlas.scheduler.gateway.configuration.JWTUtils;
import com.atlas.scheduler.gateway.exceptions.InvalidCredentialsException;
import com.atlas.scheduler.gateway.model.UserDetail;
import com.atlas.scheduler.gateway.model.UserDto;
import com.atlas.scheduler.gateway.repository.ERole;
import com.atlas.scheduler.gateway.repository.RoleRepository;
import com.atlas.scheduler.gateway.repository.Roles;
import com.atlas.scheduler.gateway.repository.UserEntity;
import com.atlas.scheduler.gateway.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	JWTUtils jwtUtils;

	@Autowired
	AuthenticationManager authenticationManager;

	@Override
	public UserDto createUser(UserDto userReq) {
		userReq.setUserId(UUID.randomUUID().toString());
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity user = mapper.map(userReq, UserEntity.class);
		user.setEncryptedPassword(encoder.encode(userReq.getPassword()));
		Set<Roles> roleEntity = getRole(userReq.getRole());
		user.setRoles(roleEntity);
		userRepo.save(user);
		return mapper.map(user, UserDto.class);
	}

	@Override
	public UserDetail loadUserByUsername(String userId) {
		UserEntity user = userRepo.findByUserId(userId);
		if (user == null) {
			throw new UsernameNotFoundException("User Name not found");
		}
		UserDetail userDetail = new UserDetail(user.getUserId(), user.getEncryptedPassword(), user.getRoles().stream()
				.map(r -> new SimpleGrantedAuthority(r.getName().name())).collect(Collectors.toList()));
		userDetail.setRoles(user.getRoles().stream().map(r ->  "ROLE_" + r.getName().name() ).collect(Collectors.joining(",")));
		return userDetail;
	}

	private Set<Roles> getRole(Set<String> roles) {
		Set<Roles> userRoles = new HashSet<>();
		if (roles == null) {
			Roles userRole = roleRepository.findByName(ERole.USER);
			userRoles.add(userRole);
		} else {
			roles.forEach(role -> {
				switch (role) {
				case "admin":
					Roles adminRole = roleRepository.findByName(ERole.ADMIN);
					userRoles.add(adminRole);
					break;
				default:
					Roles userRole = roleRepository.findByName(ERole.USER);
					userRoles.add(userRole);
				}
			});
		}
		return userRoles;
	}

	@Override
	public String authenticate(UserDto userReq) throws InvalidCredentialsException {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(userReq.getUserId(), userReq.getPassword()));
		} catch (BadCredentialsException e) {
			log.error("Invalid credentials ", e);
			throw new InvalidCredentialsException("Incorrect username or password");
		}
		final UserDetail userDetails = loadUserByUsername(userReq.getUserId());
		return jwtUtils.generateToken(userDetails);
	}

}
