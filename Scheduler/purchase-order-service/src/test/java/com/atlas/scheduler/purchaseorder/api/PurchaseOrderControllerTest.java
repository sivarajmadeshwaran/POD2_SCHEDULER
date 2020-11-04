package com.atlas.scheduler.purchaseorder.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.atlas.scheduler.purchaseorder.datatransfer.PurchaseOrderDto;
import com.atlas.scheduler.purchaseorder.service.PurchaseOrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PurchaseOrderController.class)
@AutoConfigureMockMvc
class PurchaseOrderControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	PurchaseOrderServiceImpl service;

	ObjectMapper mapper = new ObjectMapper();

	@Test
	@DisplayName("Get the Po details")
	void getPos() throws Exception {
		String uri = "/purchaseorder/find/poNbrs";
		List<Integer> requestPos = new ArrayList<>();
		requestPos.add(1);

		String json = mapper.writeValueAsString(requestPos);

		List<PurchaseOrderDto> responseDtos = new ArrayList<PurchaseOrderDto>();
		responseDtos.add(new PurchaseOrderDto());

		when(service.getPoDetailsByNbrs(requestPos)).thenReturn(responseDtos);

		mvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}
	
	

	@Test
	@DisplayName("Requested Po Not Available")
	void poNotAvailable() throws Exception {
		String uri = "/purchaseorder/find/poNbrs";
		List<Integer> requestPos = new ArrayList<>();
		requestPos.add(1);
		String json = mapper.writeValueAsString(requestPos);
		when(service.getPoDetailsByNbrs(requestPos)).thenReturn(null);
		mvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

	}
	
	@Test
	@DisplayName("Empty Po List Returned")
	void emptyListReturned() throws Exception {
		String uri = "/purchaseorder/find/poNbrs";
		List<Integer> requestPos = new ArrayList<>();
		requestPos.add(1);
		String json = mapper.writeValueAsString(requestPos);
		when(service.getPoDetailsByNbrs(requestPos)).thenReturn(new ArrayList<PurchaseOrderDto>());
		mvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

	}

}
