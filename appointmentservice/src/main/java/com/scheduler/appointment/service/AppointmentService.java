package com.scheduler.appointment.service;


import com.scheduler.appointment.Dto.AppointmentDto;

public interface AppointmentService {

	Object createAppointment(AppointmentDto appointmentDto);

	Object getAllAppointment();

	void deleteAppointment(Integer id);

	void updateAppointment(AppointmentDto appointmentDto, int id);

}
