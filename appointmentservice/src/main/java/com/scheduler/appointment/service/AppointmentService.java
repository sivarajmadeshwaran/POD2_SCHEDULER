package com.scheduler.appointment.service;


import com.scheduler.appointment.Dto.AppointmentDto;
import com.scheduler.appointment.exception.BusinessException;

public interface AppointmentService {

	Object createAppointment(AppointmentDto appointmentDto) throws BusinessException;

	Object getAllAppointment();

	void deleteAppointment(Integer id) throws BusinessException;

	void updateAppointment(AppointmentDto appointmentDto, int id);

}
