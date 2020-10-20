package com.scheduler.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.scheduler.service.DcSlotService;

@RestController
@RequestMapping("/api")
public class DcSlotController {
	
	@Autowired
	private DcSlotService dcSlotService;
	
	@Autowired
	private DcSlotRepository dcSlotRepository;
	
	@GetMapping("/getDcSlots")
	public List<DcSlot> getDcSlots() {
		return dcSlotService.getDcSlotList();
	}

	@PostMapping("/addDcSlot")
	public String addDcSlot(@RequestBody DcSlot dcSlot) {
		return dcSlotService.saveDcSlot(dcSlot);
	}
	
	@Transactional
	@PutMapping("/updateDcSlot/{id}/{bSlot}")
	public void updateDcSlot(@RequestBody DcSlot dcSlot, @PathVariable ("id") int id, @PathVariable ("bSlot") String bSlot) {
		List<DcSlot> existId = this.dcSlotRepository.findBySlotIdDcNbr(id);
		dcSlotRepository.updateDcSlot(existId.get(0).getSlotId().getDcNbr(),dcSlot.getMaxTrucks());
		
	}
	
	@Transactional
	@DeleteMapping("/deleteDcSlot/{id}/{bSlot}")
	public void deleteDcSlot(@PathVariable ("id") int id, @PathVariable ("bSlot") String bSlot) {
		List<DcSlot> existId = this.dcSlotRepository.findBySlotIdDcNbr(id);
		dcSlotRepository.deleteDcSlot(existId.get(0).getSlotId().getDcNbr());
		
	}


}
