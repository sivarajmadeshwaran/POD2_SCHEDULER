package com.scheduler.appointment.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scheduler.appointment.entity.AppointmentSlot;
import com.scheduler.appointment.repo.AppointmentSlotRepo;
import com.scheduler.appointment.service.AppointmentSlotService;

@Service
public class AppointmentSlotServiceImpl implements AppointmentSlotService{

	@Autowired
	AppointmentSlotRepo appointmentSlotRepo;
	
	@Override
	public List<AppointmentSlot> getAvailableSlots(String dcNumber) {
			
		List<AppointmentSlot> appointmentSlotDto = appointmentSlotRepo.getSlotsByDcNumber(dcNumber);		
		return appointmentSlotDto;
	}

}
