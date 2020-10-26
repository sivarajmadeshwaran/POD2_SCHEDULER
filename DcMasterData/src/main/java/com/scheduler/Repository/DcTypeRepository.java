package com.scheduler.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scheduler.entity.DcType;

@Repository
public interface DcTypeRepository extends CrudRepository<DcType, Integer> {

}
