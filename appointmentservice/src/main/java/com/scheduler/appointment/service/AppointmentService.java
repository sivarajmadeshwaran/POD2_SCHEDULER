package com.scheduler.appointment.service;


import com.scheduler.appointment.Dto.AppointmentDto;
import com.scheduler.appointment.exception.BusinessException;

/**
 * <h1>Appointment service interface</h1>
 *
 */
public interface AppointmentService {

	Object createAppointment(AppointmentDto appointmentDto) throws BusinessException;

	Object getAllAppointment();

	void deleteAppointment(Integer id) throws BusinessException;

	void updateAppointment(AppointmentDto appointmentDto, int id);

}
