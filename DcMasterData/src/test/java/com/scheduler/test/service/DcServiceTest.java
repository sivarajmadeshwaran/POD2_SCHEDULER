package com.scheduler.test.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scheduler.entity.Dc;
import com.scheduler.entity.DcType;
import com.scheduler.exception.ResourceNotFoundException;
import com.scheduler.service.DcService;
import com.scheduler.service.DcServiceImpl;

@SpringBootTest
public class DcServiceTest {

	Dc dc;
	DcType dcType;
	
	@Autowired
	private DcServiceImpl dcServiceImpl;
	
	@BeforeEach
	public void setup() {
		
		dcType=new DcType();
	    dcType.setId(2);
	    dcType.setTypeDesc("Regional");
	    dcType.setCreatedAt(new Date());
	    
	    dc=new Dc();
	    dc.setDc_number(6026);
	    dc.setDc_city("US");
	    dc.setDcTypeBean(dcType);
	    dc.setCreatedAt(new Date());
	    dc.setUpdatedAt(new Date());
	    
	    
	    List<Dc> dcs=new ArrayList<>();
	    dcs.add(dc);
	}
	
	
	@Test
	public void testCreateDc() throws Exception {
		dcServiceImpl.createNewDC(dc);
	}

	@Test
	public void testGetAllDcs() {
		dcServiceImpl.findDcs();
	}

	@Test
	public void testUpdateDc() throws ResourceNotFoundException {
		dcServiceImpl.updateDc(dc, 6026);
	}
	
	@Test
	public void testDeleteDc() throws ResourceNotFoundException {
		dcServiceImpl.removeDcById(6026);
	}


}
