package com.scheduler.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scheduler.entity.Truck;

/**
 *  This is to Interact with truck table via JPA 
*/

@Repository
public interface TruckRepository extends JpaRepository<Truck, Integer> {

}
