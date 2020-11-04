package com.atlas.scheduler.gateway.intg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.atlas.scheduler.gateway.SchedulerApiGateway;
import com.atlas.scheduler.gateway.model.AuthenticationResponse;
import com.atlas.scheduler.gateway.model.UserDto;
import com.atlas.scheduler.gateway.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = SchedulerApiGateway.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class UserControllerIntgTest {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	@Autowired
	UserServiceImpl service;

	UserDto userDtoResponse = new UserDto();
	AuthenticationResponse authResponse = null;
	ObjectMapper mapper = new ObjectMapper();

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	@Test
	@Order(1)
	@DisplayName("User Creation Success Scenario")
	void createuser() throws Exception {
		String uri = "/user";
		UserDto user = new UserDto();
		user.setFirstName("sivaraj");
		user.setPassword("123456");
		HttpEntity<UserDto> requestEntity = new HttpEntity<UserDto>(user, null);
		ResponseEntity<UserDto> response = restTemplate.exchange(createURLWithPort(uri), HttpMethod.POST, requestEntity,
				UserDto.class);
		userDtoResponse = response.getBody();
		System.out.println("User Response --- " + userDtoResponse.toString());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	@Order(2)
	@DisplayName("User Authentication Success Scenario with returning JWT token")
	void getToken() throws Exception {
		String uri = "/user/authenticate";
		userDtoResponse.setUserId("f2c82456-0c12-4c6a-8a6b-a1aa59f9bcf5");
		userDtoResponse.setPassword("12345678");
		HttpEntity<UserDto> requestEntity = new HttpEntity<UserDto>(userDtoResponse, null);
		ResponseEntity<AuthenticationResponse> response = restTemplate.exchange(createURLWithPort(uri), HttpMethod.POST,
				requestEntity, AuthenticationResponse.class);
		authResponse = response.getBody();
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@Order(3)
	@DisplayName("User Access Denied for No JWT Token request")
	void forbiddenErrorForNonTokenCall() throws Exception {
		String uri = "/user/hello";
		ResponseEntity<String> response = restTemplate.getForEntity(createURLWithPort(uri), String.class);
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}

	@Test
	@Order(4)
	@DisplayName("User Access Success with Valid JWT token")
	void getTokenThenAuthenticate() throws Exception {
		String uri = "/user/authenticate";
		userDtoResponse.setUserId("f2c82456-0c12-4c6a-8a6b-a1aa59f9bcf5");
		userDtoResponse.setPassword("12345678");
		HttpEntity<UserDto> requestEntity = new HttpEntity<UserDto>(userDtoResponse, null);
		ResponseEntity<AuthenticationResponse> response = restTemplate.exchange(createURLWithPort(uri), HttpMethod.POST,
				requestEntity, AuthenticationResponse.class);
		authResponse = response.getBody();

		// Call the Get service with token based Auth
		String helloUri = "/user/hello";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer" + authResponse.getJwt());
		HttpEntity<String> helloRequestEntity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> helloResponse = restTemplate.exchange(createURLWithPort(helloUri), HttpMethod.GET,
				helloRequestEntity, String.class);
		assertEquals("Hello", helloResponse.getBody());
	}

	@Test
	@Order(5)
	@DisplayName("User Access Denied with Invalid JWT token")
	void testWithInvalidJwt() throws Exception {

		// Call the Get service with token based Auth
		String helloUri = "/user/hello";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Hellojbsjkfbsd");
		HttpEntity<String> helloRequestEntity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> helloResponse = restTemplate.exchange(createURLWithPort(helloUri), HttpMethod.GET,
				helloRequestEntity, String.class);
		assertEquals(HttpStatus.FORBIDDEN, helloResponse.getStatusCode());

	}

	@Test
	@Order(6)
	@DisplayName("User Access Denied with Expired JWT token")
	void checkTokenExpiryScenario() throws Exception {
		String uri = "/user/authenticate";
		userDtoResponse.setUserId("f2c82456-0c12-4c6a-8a6b-a1aa59f9bcf5");
		userDtoResponse.setPassword("12345678");
		HttpEntity<UserDto> requestEntity = new HttpEntity<UserDto>(userDtoResponse, null);
		ResponseEntity<AuthenticationResponse> response = restTemplate.exchange(createURLWithPort(uri), HttpMethod.POST,
				requestEntity, AuthenticationResponse.class);
		authResponse = response.getBody();

		// Holding this to make expiry of JWT
		CountDownLatch latch = new CountDownLatch(1);
		latch.await(5, TimeUnit.SECONDS);
		// Call the Get service with token based Auth
		String helloUri = "/user/hello";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer" + authResponse.getJwt());
		HttpEntity<String> helloRequestEntity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> helloResponse = restTemplate.exchange(createURLWithPort(helloUri), HttpMethod.GET,
				helloRequestEntity, String.class);
		assertEquals(HttpStatus.FORBIDDEN, helloResponse.getStatusCode());
	}

}
