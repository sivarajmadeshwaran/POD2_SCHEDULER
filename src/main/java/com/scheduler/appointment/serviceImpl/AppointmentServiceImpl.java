package com.scheduler.appointment.serviceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scheduler.appointment.Dto.AppointmentDto;
import com.scheduler.appointment.Dto.AppointmentSlotDto;
import com.scheduler.appointment.entity.Appointment;
import com.scheduler.appointment.entity.AppointmentSlot;
import com.scheduler.appointment.repo.AppointmentRepo;
import com.scheduler.appointment.service.AppointmentService;
import com.scheduler.appointment.service.AppointmentSlotService;

@Service
public class AppointmentServiceImpl implements AppointmentService{
	
	@Autowired
	AppointmentSlotService appointmentSlotService;
	
	@Autowired
	AppointmentRepo appointmentRepo;


	@Override
	public Object createAppointment(AppointmentDto appointmentDto) {
		List<AppointmentSlot> apptSlotList =appointmentSlotService.getAvailableSlots(appointmentDto.getDcNumber());
		ModelMapper modelMapper = new ModelMapper();
		appointmentDto.setBookingSlot(apptSlotList.get(0).getId());
		appointmentDto.setLastUpdateTimeStamp("2020-10-16");
		appointmentDto.setCreatedTimeStamp("2020-10-16");
		AppointmentSlotDto appointmentSlotDto= new AppointmentSlotDto();
//		appointmentSlotDto.setCreatedTimeStamp(appointmentDto.getCreatedTimeStamp());
//		appointmentSlotDto.setLastUpdatedTimeStamp(appointmentDto.getLastUpdateTimeStamp());
		appointmentSlotDto.setBookingSlot(apptSlotList.get(0).getBookingSlot());
//		appointmentSlotDto.setDcNumber((apptSlotList.get(0).getDcNumber()));
//		appointmentSlotDto.setMaxTruckCount((apptSlotList.get(0).getMaxTruckCount()));
//		appointmentDto.setAppointmentSlotDto(appointmentSlotDto);
		appointmentSlotDto.setId((apptSlotList.get(0).getId()));
		appointmentDto.setAppointmentSlotDto(appointmentSlotDto);
		Appointment appointment = new Appointment();
		AppointmentSlot appointmentSlot = new AppointmentSlot();
		appointmentSlot.setId(apptSlotList.get(0).getId());
		appointment.setAppointmentSlot(apptSlotList.get(0));
		appointment.setDcNumber(appointmentDto.getDcNumber());
		appointment.setAppointmentDate(appointmentDto.getAppointmentDate());
		appointment.setCreatedTimeStamp(appointmentDto.getCreatedTimeStamp());
		appointment.setLastUpdateTimeStamp(appointmentDto.getLastUpdateTimeStamp());
		
		
		
//		Appointment appointment = modelMapper.map(appointmentDto, Appointment.class);

		
		appointmentRepo.save(appointment);
		return null;	
	}

}
