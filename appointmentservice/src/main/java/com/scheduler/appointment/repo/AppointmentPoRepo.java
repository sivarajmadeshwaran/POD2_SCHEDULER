package com.scheduler.appointment.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.scheduler.appointment.entity.AppoinmentPoPk;
import com.scheduler.appointment.entity.AppointmentPo;

public interface AppointmentPoRepo extends CrudRepository<AppointmentPo, AppoinmentPoPk> {

	@Query(value = "select * from appointment_po where appointment_id =:appointmentId", nativeQuery = true)
	List<AppointmentPo> getAppointmentPoById(@Param("appointmentId")Integer id);

}
