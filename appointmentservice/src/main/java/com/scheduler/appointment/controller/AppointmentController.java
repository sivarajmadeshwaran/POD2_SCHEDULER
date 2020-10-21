package com.scheduler.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.appointment.Dto.AppointmentDto;
import com.scheduler.appointment.exception.BusinessException;
import com.scheduler.appointment.service.AppointmentService;

@RestController
public class AppointmentController {
	
	@Autowired
	AppointmentService appointmentService;

	@PostMapping(path = "/createAppointment",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createAppointment(@RequestBody AppointmentDto appointmentDto) throws BusinessException {
		  return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createAppointment(appointmentDto));
	}
	
	@GetMapping("/getAllAppointment")
	public ResponseEntity<Object> searchAll(){
		 return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.getAllAppointment());
		
	}
	@DeleteMapping("/delete/{id}")
	public void deleteAppointment(@PathVariable Integer id){
		 appointmentService.deleteAppointment(id);
		
	}
	@PutMapping("/updateAppointment/{id}")
	public void updateAppointment(@RequestBody AppointmentDto appointmentDto,@PathVariable int id){
		  appointmentService.updateAppointment(appointmentDto,id);
	}
}
