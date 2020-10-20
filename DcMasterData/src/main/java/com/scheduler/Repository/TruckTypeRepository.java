package com.scheduler.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scheduler.entity.TruckType;

@Repository
public interface TruckTypeRepository extends JpaRepository<TruckType, Integer>{

}
