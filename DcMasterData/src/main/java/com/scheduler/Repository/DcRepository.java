package com.scheduler.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.scheduler.entity.Dc;

/**
 *  This is to Interact with dc table via JPA 
*/

@Repository
public interface DcRepository extends CrudRepository<Dc, Integer>{

}
