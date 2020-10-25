package com.atlas.scheduler.gateway.repository;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Integer>{
	
	UserEntity findByUserId(String userId);

}
