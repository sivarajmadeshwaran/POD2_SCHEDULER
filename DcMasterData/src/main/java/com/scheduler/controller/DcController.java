package com.scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.scheduler.entity.Dc;
import com.scheduler.exception.ResourceExistsException;
import com.scheduler.exception.ResourceNotFoundException;
import com.scheduler.service.DcService;

/**
 * This is  to expose Dc Domain related API's
*/

@RestController
@RequestMapping("/setup/dc")
public class DcController {
	
	@Autowired
	private DcService dcservice;
	

	// Create new Dc
	@PostMapping
	public ResponseEntity<Object> createDc(@RequestBody Dc dc) throws ResourceExistsException, Exception {
		dcservice.createNewDC(dc);
		return new ResponseEntity<>("Dc detail is added successfully", HttpStatus.CREATED);
	}

	// Find all Dcs
	@GetMapping
	public ResponseEntity<Object> findAlldcs() {
		return new ResponseEntity<>(dcservice.findDcs(), HttpStatus.OK);
	}

	// Find Dc by Id
	@GetMapping("/{id}")
	public ResponseEntity<Object> getDcById(@PathVariable (value="id") int id) throws ResourceNotFoundException{
		return new ResponseEntity<>(dcservice.findDcById(id), HttpStatus.OK);
	}
	
	// Modify Dc detail
	@PutMapping("/{id}")
	public ResponseEntity<Object> modifyDc(@RequestBody Dc dc, @PathVariable ("id") int dc_id) throws ResourceNotFoundException {
		dcservice.updateDc(dc,dc_id);
		return new ResponseEntity<>("Dc detail is updated successsfully", HttpStatus.OK);
	}
	
	// Remove Dc
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> removeDc(@PathVariable int id) throws ResourceNotFoundException {
		dcservice.removeDcById(id);
		return new ResponseEntity<>("Dc detail removed successfully", HttpStatus.OK);
	}

}