package com.scheduler.appointment.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.scheduler.appointment.Dto.AppointmentSlotDto;
import com.scheduler.appointment.Dto.PurchaseOrderDto;
import com.scheduler.appointment.entity.AppointmentSlot;

import feign.hystrix.FallbackFactory;

@FeignClient(name = "dc-slot-service", fallbackFactory = DcSlotFallBackFactory.class)
@Component
public interface DcSlotFeignClient {
	@GetMapping(value = "/setup/dcslot/{id}")
	List<AppointmentSlotDto> findDcSlotById(@PathVariable int id);
}

@Component
class DcSlotFallBackFactory implements FallbackFactory<DcSlotFeignClient> {
	@Override
	public DcSlotFeignClient create(Throwable arg0) {
		return new DcSlotFeignClient() {
			@Override
			public List<AppointmentSlotDto> findDcSlotById(int id) {
				List<AppointmentSlotDto> slotList = new ArrayList<>();
				slotList.add(new AppointmentSlotDto());
				return slotList;
			}
		};
	}
}
