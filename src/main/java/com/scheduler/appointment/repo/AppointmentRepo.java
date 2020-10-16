package com.scheduler.appointment.repo;

import org.springframework.data.repository.CrudRepository;

import com.scheduler.appointment.entity.Appointment;

public interface AppointmentRepo extends CrudRepository<Appointment, Integer>{

}
