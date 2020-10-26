package com.scheduler.service;

import com.scheduler.entity.Dc;
import com.scheduler.exception.ResourceExistsException;
import com.scheduler.exception.ResourceNotFoundException;

public interface DcService {
	public void createNewDC(Dc dc) throws ResourceExistsException, Exception;
	public Object findDcs();
	public Object findDcById(int id) throws ResourceNotFoundException;
	public void updateDc(Dc dc, int dc_id)  throws ResourceNotFoundException;
	public void removeDcById(int id) throws ResourceNotFoundException;
}
