package com.scheduler.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.appointment.Dto.AppointmentDto;
import com.scheduler.appointment.service.AppointmentService;

@RestController
public class AppointmentController {
	
	@Autowired
	AppointmentService appointmentService;

	@PostMapping(path = "/createAppointment",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAppointment(@RequestBody AppointmentDto appointmentDto) {
		  return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createAppointment(appointmentDto));
	}
}
