package com.scheduler.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.DcNotFoundException;
import com.scheduler.Repository.DcRepository;
import com.scheduler.entity.Dc;
import com.scheduler.service.DcService;


@RestController
@RequestMapping("/api")
public class DcController {
	
	@Autowired
	private DcService dcservice;
	
	@Autowired
	private DcRepository dcRepository;

	@PostMapping("/addDc")
	public String addDC(@RequestBody Dc dc) {
	return dcservice.saveDC(dc);
	}

	@PostMapping("/addDcs")
	public String addDCs(@RequestBody List<Dc> dcs) {
	return dcservice.saveDcList(dcs);
	}

	@GetMapping("/getDcs")
	public List<Dc> getDcs() {
		return dcservice.getDcList();
		}

	@DeleteMapping("/deleteDc/{id}")
	public String deleteDc(@PathVariable int id) {
		return dcservice.deleteDcById(id);
	}

	@PutMapping("/updateDc/{id}")
	public Dc updateDc(@RequestBody Dc dc, @PathVariable ("id") int dc_id) {
		Dc existId= this.dcRepository.findById(dc_id)
				.orElseThrow(() -> new DcNotFoundException("Id Is Not Available " + dc_id) );
		existId.setDc_city(dc.getDc_city());
		return this.dcRepository.save(existId);
	}
	
	@GetMapping("/getDc/{id}")
	public Optional<Dc> getDcById(@PathVariable (value="id") int id){
		return dcservice.getDcById(id);
	}


}