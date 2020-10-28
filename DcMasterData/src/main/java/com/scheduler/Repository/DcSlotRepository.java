package com.scheduler.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scheduler.entity.DcSlot;
import com.scheduler.entity.DcSlotPK;

/**
 *  This is to Interact with dc_slot table via JPA 
*/

@Repository
public interface DcSlotRepository extends JpaRepository<DcSlot, DcSlotPK>{
	List<DcSlot> findBySlotIdDcNbr(int dcNbr);
	List<DcSlot> findBySlotIdBookingSlot(String bookingSlot);
	 
	@Modifying
	@Query(value = "update dc_slot set max_trucks=:maxTrucks where dc_nbr=:existId and booking_slot=:existSlot", nativeQuery = true)
	void updateDcSlot(@Param("existId") int existId, @Param("maxTrucks") int maxTrucks, @Param("existSlot") String existSlot);

	@Modifying
	@Query(value = "update dc_slot set obsolete_indicator='Y' where dc_nbr=:existId and booking_slot=:existSlot", nativeQuery = true)
	void deleteDcSlot(@Param("existId") int existId, @Param("existSlot") String existSlot);
	
	@Query(value = "select * from dc_slot d where d.dc_nbr = :id and d.booking_slot = :bSlot",nativeQuery = true)
	List<DcSlot> getDcSlotById(@Param("id") int id,@Param("bSlot") String bSlot);
	
}
