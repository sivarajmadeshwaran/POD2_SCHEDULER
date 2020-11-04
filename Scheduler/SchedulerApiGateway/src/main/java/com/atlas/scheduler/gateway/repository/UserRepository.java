package com.atlas.scheduler.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{
	
	UserEntity findByUserId(String userId);

}
