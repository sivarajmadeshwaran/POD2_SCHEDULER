package com.scheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scheduler.Repository.DcSlotRepository;
import com.scheduler.entity.DcSlot;
import com.scheduler.exception.ResourceNotFoundException;

/**
 * This is for DcSlot Domain Business Implementation
*/

@Service
public class DcSlotServiceImpl implements DcSlotService{

	@Autowired
	private DcSlotRepository dcSlotRepository;
	
	/**
	 *  This is to persist the DcSlot detail.
	 * @param DcSlot DTO
	 * @return void
	*/
	public void addDcSlot(DcSlot dcSlot) throws Exception{
		
		String slotTime=dcSlot.getSlotId().getBookingSlot();
		if(slotTime.length()==11) {
			dcSlotRepository.save(dcSlot);
		}
		else throw new Exception("Booking Slot Time - Out of range");
	}
	
	/**
	 *  This is to get all the vendor details
	*/
	public Object getDcSlots() {
		return dcSlotRepository.findAll();
	}


}
