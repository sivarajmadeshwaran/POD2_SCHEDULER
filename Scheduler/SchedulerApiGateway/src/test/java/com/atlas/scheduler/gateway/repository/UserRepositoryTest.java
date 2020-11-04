package com.atlas.scheduler.gateway.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
class UserRepositoryTest {

	@Autowired
	private UserRepository repository;

	UserEntity user = null;

	@BeforeEach
	void setup() {
		user = new UserEntity();
		user.setFirstName("Siva");
		user.setLastName("Raj");
		user.setEncryptedPassword("123456");
		user.setUserId("siva123");
	}

	@Test
	@Order(1)
	@DisplayName("Save User Success Scenario")
	void testCreateUser() {
		UserEntity savedEntity = repository.save(user);
		assertNotNull(savedEntity);
		assertEquals("siva123", savedEntity.getUserId());
	}

	@Test
	@Order(2)
	@DisplayName("Find User Success Scenario")
	void testFindUser() {
		UserEntity savedEntity = repository.findByUserId("siva123");
		assertNotNull(savedEntity);
		assertNotNull(savedEntity.getUserId());
	}

	@Test
	@Order(3)
	@DisplayName("User Created with Role Success Scenario")
	void testCreateUserWithRole() {
		Roles role = new Roles();
		role.setId(1);
		role.setName(ERole.ADMIN);
		Set<Roles> rols = new HashSet<>();
		rols.add(role);
		user.setRoles(rols);
		UserEntity savedEntity = repository.save(user);
		assertNotNull(savedEntity);
		assert (savedEntity.getRoles().size() > 0);
	}

	@Test
	@Order(4)
	void testUserEntityEquals() {
		UserEntity user = new UserEntity();
		user.setUserId("siva");
		UserEntity user1 = new UserEntity();
		user1.setUserId("siva");
		UserEntity user2 = new UserEntity();
		user2.setUserId("sivaraj");
		assertEquals(user.hashCode(), user1.hashCode());
		assertEquals(user, user1);
		assertNotEquals(user, user2);
	}

	@Test
	@Order(5)
	void testRolesEntityEquals() {
		Roles role = new Roles();
		role.setId(1);
		Roles role1 = new Roles();
		role1.setId(1);
		Roles role2 = new Roles();
		role2.setId(3);

		assertEquals(role.hashCode(), role1.hashCode());
		assertEquals(role, role1);
		assertNotEquals(role, role2);
	}

}
