package com.scheduler.service;

import com.scheduler.entity.DcSlot;

public interface DcSlotService {
	
	public void addDcSlot(DcSlot dcSlot) throws Exception;
	public Object getDcSlots();
	
}
