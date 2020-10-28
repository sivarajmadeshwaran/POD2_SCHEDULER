package com.scheduler.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

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

import com.scheduler.Repository.DcSlotRepository;
import com.scheduler.entity.DcSlot;
import com.scheduler.exception.ResourceNotFoundException;
import com.scheduler.service.DcSlotService;

/**
 * This is  to expose DcSlot Domain related API's
*/

@RestController
@RequestMapping("/setup/dcslot")
public class DcSlotController {
	
	@Autowired
	private DcSlotService dcSlotService;
	
	@Autowired
	private DcSlotRepository dcSlotRepository;
	
	// Create new DcSlot
	@PostMapping
	public ResponseEntity<Object> createDcSlot(@RequestBody DcSlot dcSlot) throws Exception {
		dcSlotService.addDcSlot(dcSlot);
		return new ResponseEntity<>("DcSlot detail is added successfully", HttpStatus.CREATED);
	}
	
	// Find all DcSlots
	@GetMapping
	public ResponseEntity<Object> findAllDcSlots() {
		return new ResponseEntity<>(dcSlotService.getDcSlots(), HttpStatus.OK);
	}
	
	// Get DcSlot detail by dcNbr
	@GetMapping("/{id}")
	public List<DcSlot> findDcSlotById(@PathVariable ("id") int id) throws ResourceNotFoundException {
		List<DcSlot> result = dcSlotRepository.getDcSlotById(id);
		if(result.isEmpty()) {
			  throw new ResourceNotFoundException("DcSlot detail is not found for : "+ id  );
		  }
		  return dcSlotRepository.getDcSlotById(id);
	}

	
	// Modify DcSlot detail
	@Transactional
	@PutMapping("/{id}/{bSlot}")
	public void updateDcSlot(@RequestBody DcSlot dcSlot, @PathVariable ("id") int id, @PathVariable ("bSlot") String bSlot) {
		List<DcSlot> existId = this.dcSlotRepository.findBySlotIdDcNbr(id);
		List<DcSlot> existSlot = this.dcSlotRepository.findBySlotIdBookingSlot(bSlot);
		dcSlotRepository.updateDcSlot(existId.get(0).getSlotId().getDcNbr(),dcSlot.getMaxTrucks(),existSlot.get(0).getSlotId().getBookingSlot());
		
	}

	// Remove DcSlot
	@Transactional
	@DeleteMapping("/{id}/{bSlot}")
	public void deleteDcSlot(@PathVariable ("id") int id, @PathVariable ("bSlot") String bSlot) {
		List<DcSlot> existId = this.dcSlotRepository.findBySlotIdDcNbr(id);
		List<DcSlot> existSlot = this.dcSlotRepository.findBySlotIdBookingSlot(bSlot);
		dcSlotRepository.deleteDcSlot(existId.get(0).getSlotId().getDcNbr(),existSlot.get(0).getSlotId().getBookingSlot());
		
	}

}
