package com.scheduler.test.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scheduler.Repository.DcSlotRepository;
import com.scheduler.entity.DcSlot;
import com.scheduler.entity.DcSlotPK;
import com.scheduler.service.DcSlotServiceImpl;

@SpringBootTest
public class DcSlotServiceTest {

	DcSlot dcSlot;
	DcSlotPK pk;
	
	@Autowired
	private DcSlotServiceImpl dcSlotServiceImpl;
	
	@Autowired
	private DcSlotRepository dcSlotRepo;
	
	@BeforeEach
	public void setup() {
		pk=new DcSlotPK();
		pk.setDcNbr(6561);
		pk.setBookingSlot("08:00");
		
		dcSlot = new DcSlot();
		dcSlot.setMaxTrucks(10);
		dcSlot.setSlotId(pk);
		dcSlot.setObsolete_indicator("N");
		dcSlot.setCreatedTimestamp(new Date());
		dcSlot.setLastUpdatedTimestamp(new Date());
		
		List<DcSlot> dcSlots=new ArrayList<>();
		dcSlots.add(dcSlot);
	}
	
	@Test
	public void testGetAllDcSlots() {
		dcSlotServiceImpl.getDcSlots();
	}

	@Test
	public void testCreateDcSlot() throws Exception {
		dcSlotServiceImpl.addDcSlot(dcSlot);
	}
	
/*	@Test
	public void testUpdateDcSlot() {
		dcSlotRepo.updateDcSlot(6026, 12);
	}
*/	
}

