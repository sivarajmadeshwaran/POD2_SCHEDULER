package com.scheduler.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.scheduler.Repository.DcRepository;
import com.scheduler.entity.Dc;

@Component
public class DcService{

	@Autowired
	private DcRepository dcRepository;
		
	public String saveDC(Dc dc) {
		System.out.println("Inputs : " + dc);
		dcRepository.save(dc);
		return "DC is added";
	}

	public String saveDcList(List<Dc> dcs) {
		System.out.println("Inputs : " + dcs);
		dcRepository.saveAll(dcs);
		return "DC size is : " + dcs.size();
	}

	public List<Dc> getDcList() {
		return (List<Dc>) dcRepository.findAll();
	}

	public String deleteDcById(int id) {
			dcRepository.deleteById(id);
		return "DC removed !!" + id;
	}

	public Dc updateDcValue(Dc dc) {
		Dc existingDC = dcRepository.findById(dc.getDc_number()).orElse(null);
		existingDC.setDc_city(dc.getDc_city());
		existingDC.setDcTypeBean(dc.getDcTypeBean());
		return dcRepository.save(existingDC);
	}

	public Optional<Dc> getDcById(int id) {
		return this.dcRepository.findById(id);
	}
		

}