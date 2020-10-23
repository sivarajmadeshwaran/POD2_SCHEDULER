package com.scheduler.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scheduler.entity.DcSlot;
import com.scheduler.entity.DcSlotPK;

@Repository
public interface DcSlotRepository extends JpaRepository<DcSlot, DcSlotPK>{
	List<DcSlot> findBySlotIdDcNbr(int dcNbr);

	@Modifying
	@Query(value = "update dc_slot set max_trucks=:maxTrucks where dc_nbr=:existId", nativeQuery = true)
	void updateDcSlot(@Param("existId") int existId, @Param("maxTrucks") int maxTrucks);

	@Modifying
	@Query(value = "update dc_slot set obsolete_indicator='Y' where dc_nbr=:existId", nativeQuery = true)
	void deleteDcSlot(@Param("existId") int existId);


	
}
