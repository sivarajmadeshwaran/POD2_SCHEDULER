package com.scheduler.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scheduler.entity.Truck;

@Repository
public interface TruckRepository extends JpaRepository<Truck, Integer> {

}
