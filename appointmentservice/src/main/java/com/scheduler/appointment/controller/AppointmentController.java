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
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.appointment.Dto.AppointmentDto;
import com.scheduler.appointment.exception.BusinessException;
import com.scheduler.appointment.service.AppointmentService;



/**
 * <h1>Appointment creation CRUD operation controller</h1>
 *
 */
@RestController
public class AppointmentController {
	@Autowired
	AppointmentService appointmentService;

	/**
	 * <h1>Appointment creation method</h1> creating appointment based on dc_slot
	 * max truck count
	 * 
	 * @param appointmentDto
	 * @return Appointment object
	 */
	@PostMapping(path = "/createAppointment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createAppointment(@RequestBody AppointmentDto appointmentDto)
			throws BusinessException {
		return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createAppointment(appointmentDto));
	}

	/**
	 * Getting all appointment
	 * 
	 * @return will return all appointments from appointment table
	 */
	@GetMapping("/getAllAppointment")
	public ResponseEntity<Object> searchAll() {
		return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.getAllAppointment());

	}

	/**
	 * <h1>Delete appointment by ID</h1>
	 * 
	 * @param id
	 * @return will return all appointments from appointment table
	 */
	@DeleteMapping("/delete/{id}")
	public void deleteAppointment(@PathVariable Integer id) throws BusinessException {
		appointmentService.deleteAppointment(id);

	}

	/**
	 * <h1>updating appointment by ID</h1>
	 * 
	 * @param id,appointmentDto
	 * 
	 */
	@PutMapping("/updateAppointment/{id}")
	public void updateAppointment(@RequestBody AppointmentDto appointmentDto, @PathVariable int id) {
		appointmentService.updateAppointment(appointmentDto, id);
	}
}
