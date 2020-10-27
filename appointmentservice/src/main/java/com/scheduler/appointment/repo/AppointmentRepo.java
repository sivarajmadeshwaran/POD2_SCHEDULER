package com.scheduler.appointment.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.scheduler.appointment.entity.Appointment;


/**
 * <h1>Appointment CRUD Repository </h1>
 *
 */
public interface AppointmentRepo extends CrudRepository<Appointment, Integer> {

	@Query(value = "select count(*) from appointment where booking_slot_id = :id", nativeQuery = true)
	int getCountBySlotId(@Param("id") int id);

	@Modifying
	@Query(value = "update appointment set qty=:qty,appt_date =:apptDate where appointment_id =:id", nativeQuery = true)
	void updateAppointment(@Param("qty") int totalQty, @Param("id") int id,@Param("apptDate") Date appointmentDate);

}
