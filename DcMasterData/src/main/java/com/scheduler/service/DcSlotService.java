package com.scheduler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scheduler.Repository.DcSlotRepository;
import com.scheduler.entity.DcSlot;

@Service
public class DcSlotService {

	@Autowired
	private DcSlotRepository dcSlotRepository;
	
	public String saveDcSlot(DcSlot dcSlot) {
		dcSlotRepository.save(dcSlot);
		return "DcSlot is added";
	}
	
	public List<DcSlot> getDcSlotList() {
		return (List<DcSlot>) dcSlotRepository.findAll();
	}

}
