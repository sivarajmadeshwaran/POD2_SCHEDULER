package com.scheduler.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;  
import org.springframework.data.domain.Pageable;  
import org.springframework.data.jpa.repository.JpaRepository;  
import org.springframework.data.jpa.repository.Modifying;  
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import com.scheduler.entity.Dc;

@Repository
public interface DcRepository extends CrudRepository<Dc, Integer>{

}
