package com.scheduler.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scheduler.entity.Vendor;

/**
 *  This is to Interact with vendor table via JPA 
*/

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer>{


}
