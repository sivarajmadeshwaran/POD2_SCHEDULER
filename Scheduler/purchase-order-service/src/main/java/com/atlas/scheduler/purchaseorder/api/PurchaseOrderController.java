package com.atlas.scheduler.purchaseorder.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atlas.scheduler.purchaseorder.datatransfer.PurchaseOrderDto;
import com.atlas.scheduler.purchaseorder.service.IPurchaseOrderService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * @author sivaraj
 * This is  to expose  Purchase Order Domain related   API's
 */
@RestController
@RequestMapping("/purchaseorder")
public class PurchaseOrderController {

	@Autowired
	IPurchaseOrderService orderService;

	@ApiOperation(value = "Get Purchase Order Details by List of Po Number", nickname = "getPoByNos", response = PurchaseOrderDto.class,responseContainer = "List") 
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = PurchaseOrderDto.class),
			@ApiResponse(code = 204, message = "No Content")})
	@PostMapping("/find/poNbrs")
	public ResponseEntity<List<PurchaseOrderDto>> createOrder(@RequestBody List<Integer> orders) {
		List<PurchaseOrderDto> pos = orderService.getPoDetailsByNbrs(orders);
		ResponseEntity<List<PurchaseOrderDto>> responseEntity = null;
		if (pos != null && !pos.isEmpty()) {
			responseEntity = new ResponseEntity<>(orderService.getPoDetailsByNbrs(orders), HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<List<PurchaseOrderDto>>(HttpStatus.NO_CONTENT);
		}
		return responseEntity;
	}

}
