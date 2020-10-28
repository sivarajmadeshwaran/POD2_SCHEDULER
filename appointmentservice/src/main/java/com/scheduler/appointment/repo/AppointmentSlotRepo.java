package com.scheduler.appointment.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scheduler.appointment.Dto.AppointmentSlotDto;
import com.scheduler.appointment.entity.Appointment;
import com.scheduler.appointment.entity.AppointmentSlot;


/**
 * <h1>Appointment Slot CRUD repository</h1>
 *
 */
@Repository
public interface AppointmentSlotRepo extends CrudRepository<AppointmentSlot, Integer> {

	@Query(value = "select * from dc_slot s where s.dc_nbr= :dcNumber",nativeQuery = true)
	List<AppointmentSlot> getSlotsByDcNumber(@Param("dcNumber") String dcNumber);
	
	

}
