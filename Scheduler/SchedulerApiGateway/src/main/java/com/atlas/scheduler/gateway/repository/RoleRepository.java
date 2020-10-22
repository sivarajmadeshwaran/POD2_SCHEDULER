package com.atlas.scheduler.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles, Integer>{

	Roles findByName(ERole roleUser);

}
