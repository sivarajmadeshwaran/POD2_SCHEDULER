package com.scheduler.appointment.repo;

import org.springframework.data.repository.CrudRepository;

import com.scheduler.appointment.entity.AppoinmentPoPk;
import com.scheduler.appointment.entity.AppointmentPo;

public interface AppointmentPoRepo extends CrudRepository<AppointmentPo, AppoinmentPoPk> {

}
