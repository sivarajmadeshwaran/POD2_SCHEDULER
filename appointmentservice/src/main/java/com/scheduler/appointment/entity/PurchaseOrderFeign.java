package com.scheduler.appointment.entity;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.scheduler.appointment.Dto.PurchaseOrderDto;

import feign.hystrix.FallbackFactory;

//@Component
//@FeignClient(name = "po-service", fallbackFactory = HystrixFallBackImplementation.class)
public interface PurchaseOrderFeign {
//
//	@PostMapping("/find/poNbrs")
//	public PurchaseOrderDto getPo(@RequestBody List<Integer> pos);

}
//@Component
//class HystrixFallBackImplementation implements FallbackFactory<PurchaseOrderFeign> {
//
//	@Override
//	public PurchaseOrderFeign create(Throwable cause) {
//		//
//		return new PurchaseOrderFeign() {
//
//			@Override
//			public PurchaseOrderDto getPo(List<Integer> pos) {
//				System.out.println(cause.getMessage());
//				return new PurchaseOrderDto() {
//
//					@Override
//					public PurchaseOrderDto getPo(List<Integer> pos) {
//						// TODO Auto-generated method stub
//						return new PurchaseOrderDto();
//					}
//				};
//			}
//		};
//	}
//
//}