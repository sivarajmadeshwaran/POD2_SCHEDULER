package com.scheduler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.NOT_FOUND)
public class DcSlotNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DcSlotNotFoundException(String message) {
		super(message);
	}

}