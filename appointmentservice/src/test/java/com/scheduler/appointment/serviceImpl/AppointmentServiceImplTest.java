package com.scheduler.appointment.serviceImpl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scheduler.appointment.Dto.AppointmentDto;
import com.scheduler.appointment.Dto.PurchaseOrderDto;
import com.scheduler.appointment.exception.BusinessException;
import com.scheduler.appointment.service.AppointmentService;

@SpringBootTest
public class AppointmentServiceImplTest {

	AppointmentDto appointmentDto ;
	
	@Autowired
	AppointmentService appointmentService;
	@BeforeEach
	public void setup() {
		appointmentDto = new AppointmentDto();
		appointmentDto.setBookingSlot(1);
		PurchaseOrderDto purchaseOrderDto = new PurchaseOrderDto();
		List<PurchaseOrderDto> pos =  new ArrayList<>();
		purchaseOrderDto.setPoNbr(234);
		purchaseOrderDto.setPoNbr(23232);
		purchaseOrderDto.setQty(5);
		pos.add(purchaseOrderDto);
		appointmentDto.setPos(pos);
		appointmentDto.setDcNumber("7013");
		appointmentDto.setCreatedTimeStamp("2020-10-21");
		appointmentDto.setLastUpdateTimeStamp("2020-10-21");
		appointmentDto.setAppointmentDate(new Date());
		appointmentDto.setTruckNumber(124);
	}
	
	@Test
	public void testGetAllAppointment() {
		appointmentService.getAllAppointment();
	}
	
	@Test
	public void testCreateAppointment() throws BusinessException {
		appointmentService.createAppointment(appointmentDto);
	}
	
	@Test
	public void testDeleteAppointment() {
		appointmentService.deleteAppointment(185);
	}
	
	@Test
	public void testUpdateAppointment() {
		appointmentService.updateAppointment(appointmentDto, 185);
		
	}
}

