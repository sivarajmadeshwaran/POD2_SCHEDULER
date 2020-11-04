package com.atlas.scheduler.gateway.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.atlas.scheduler.gateway.model.UserDto;
import com.atlas.scheduler.gateway.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class UserControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	UserServiceImpl service;

	ObjectMapper mapper = new ObjectMapper();

	@Test
	@Order(1)
	@DisplayName("User Creation Success Scenario")
	void createuser() throws Exception {
		String uri = "/user";
		UserDto user = new UserDto();
		user.setFirstName("sivaraj");
		user.setPassword("123456");
		String json = mapper.writeValueAsString(user);
		when(service.createUser(user)).thenReturn(user);
		mvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}

	@Test
	@Order(2)
	@DisplayName("User Authentication Success Scenario")
	void authenticate() throws Exception {
		String uri = "/user/authenticate";
		UserDto user = new UserDto();
		user.setUserId("1235353ngdfjgdk");
		user.setPassword("123456");
		String json = mapper.writeValueAsString(user);
		String token = "123bjkbjbjb34234bjkbkjjbb234";
		when(service.authenticate(user)).thenReturn(token);
		mvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	@Order(3)
	@DisplayName("User Access Denied for No JWT Token request")
	void testForibiddenForHelloService() throws Exception {
		String uri = "/user/hello";
		mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
	}
	
	@Test
	@Order(4)
	@WithMockUser("/siva")
	@DisplayName("User Access  Success")
	void successWithAuthHeader() throws Exception {
		String uri = "/user/hello";
		mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

}
