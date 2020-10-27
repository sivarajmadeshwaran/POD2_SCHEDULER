package com.scheduler.appointment.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.scheduler.appointment.Dto.PurchaseOrderDto;

import feign.hystrix.FallbackFactory;

@FeignClient(name = "purchase-order-service", fallbackFactory = PurchaseOrderFallBack.class)
@Component
public interface PurchaseOrderFeignClient {

	@PostMapping(value = "/purchaseorder/find/poNbrs")
	List<PurchaseOrderDto> getPoDetails(@RequestBody List<Integer> pos);

}


@Component
class PurchaseOrderFallBack implements FallbackFactory<PurchaseOrderFeignClient> {

	@Override
	public PurchaseOrderFeignClient create(Throwable cause) {
		return new PurchaseOrderFeignClient() {
			@Override
			public List<PurchaseOrderDto> getPoDetails(List<Integer> pos) {
				List<PurchaseOrderDto> poDetails = new ArrayList<PurchaseOrderDto>();
				poDetails.add(new PurchaseOrderDto());
				return poDetails;
			}
		};
	}

}
