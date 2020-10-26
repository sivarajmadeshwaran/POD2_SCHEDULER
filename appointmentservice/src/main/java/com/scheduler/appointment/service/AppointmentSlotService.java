package com.scheduler.appointment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scheduler.appointment.entity.AppointmentSlot;


/**
 * <h1>Appointment slot service interface</h1>
 *
 */
public interface AppointmentSlotService {

	List<AppointmentSlot> getAvailableSlots(String dcNumber);
	
	

}
