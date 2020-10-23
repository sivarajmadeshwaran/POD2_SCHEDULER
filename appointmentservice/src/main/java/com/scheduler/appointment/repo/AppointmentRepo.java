package com.scheduler.appointment.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.scheduler.appointment.Dto.AppointmentDto;
import com.scheduler.appointment.entity.Appointment;

public interface AppointmentRepo extends CrudRepository<Appointment, Integer>{

	@Query(value = "select count(*) from appointment where booking_slot_id = :id",nativeQuery = true)
	int getCountBySlotId(@Param("id")int id);
	
	@Modifying
	@Query(value="update appointment set obsolete_indicator='Y',appt_status_id =:statusId where appointment_id =:id",nativeQuery = true)
	void deleteAppointment(@Param("id") Integer id, @Param("statusId") int statusId);
	@Modifying
	@Query(value="update appointment set qty=:qty where appointment_id =:id",nativeQuery = true)
	void updateAppointment(@Param("qty") int totalQty,@Param("id") int id);



}
