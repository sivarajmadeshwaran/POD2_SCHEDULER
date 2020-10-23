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
import com.scheduler.entity.DcSlotPK;
import com.scheduler.exception.ResourceExistsException;
import com.scheduler.service.DcSlotService;

@RestController
@RequestMapping("/setup/dcslot")
public class DcSlotController {
	
	@Autowired
	private DcSlotService dcSlotService;
	
	@Autowired
	private DcSlotRepository dcSlotRepository;
	
	// Create new Dc
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

	// Modify DcSlot detail
	@Transactional
	@PutMapping("/{id}/{bSlot}")
	public void updateDcSlot(@RequestBody DcSlot dcSlot, @PathVariable ("id") int id, @PathVariable ("bSlot") String bSlot) {
		List<DcSlot> existId = this.dcSlotRepository.findBySlotIdDcNbr(id);
		dcSlotRepository.updateDcSlot(existId.get(0).getSlotId().getDcNbr(),dcSlot.getMaxTrucks());
		
	}
	
	// Remove Dc
	@Transactional
	@DeleteMapping("/{id}/{bSlot}")
	public void deleteDcSlot(@PathVariable ("id") int id, @PathVariable ("bSlot") String bSlot) {
		List<DcSlot> existId = this.dcSlotRepository.findBySlotIdDcNbr(id);
		dcSlotRepository.deleteDcSlot(existId.get(0).getSlotId().getDcNbr());
		
	}

}
