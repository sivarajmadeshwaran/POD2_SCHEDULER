package com.scheduler.appointment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scheduler.appointment.entity.AppointmentSlot;


public interface AppointmentSlotService {

	List<AppointmentSlot> getAvailableSlots(String dcNumber);
	
	

}
