package com.scheduler.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.scheduler.Repository.DcRepository;
import com.scheduler.Repository.DcTypeRepository;
import com.scheduler.entity.DcType;

@RestController
@RequestMapping("/api/dctype")
public class DcTypeController {
	
	@Autowired
	private DcTypeRepository dcTypeRepository;
	
	@Autowired
	private DcRepository dcRepository;
	
	@Autowired
	public DcTypeController(DcTypeRepository dcTypeRepository, DcRepository dcRepository) {
	        this.dcTypeRepository = dcTypeRepository;
	        this.dcRepository = dcRepository;
	   }
	
	@PostMapping
	public ResponseEntity<DcType> create(@Validated @RequestBody DcType dcType) {
		DcType savedDcType = dcTypeRepository.save(dcType);
	        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	            .buildAndExpand(savedDcType.getId()).toUri();
	        return ResponseEntity.created(location).body(savedDcType);
	    }

}
