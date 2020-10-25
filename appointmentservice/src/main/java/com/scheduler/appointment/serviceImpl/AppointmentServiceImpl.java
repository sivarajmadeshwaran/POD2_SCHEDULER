package com.scheduler.appointment.serviceImpl;

import static org.junit.jupiter.api.DynamicTest.stream;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import com.scheduler.appointment.Dto.AppointmentDto;
import com.scheduler.appointment.Dto.PurchaseOrderDto;
import com.scheduler.appointment.entity.AppoinmentPoPk;
import com.scheduler.appointment.entity.Appointment;
import com.scheduler.appointment.entity.AppointmentPo;
import com.scheduler.appointment.entity.AppointmentSlot;
import com.scheduler.appointment.entity.AppointmentStatus;
import com.scheduler.appointment.exception.BusinessException;
import com.scheduler.appointment.repo.AppointmentPoRepo;
import com.scheduler.appointment.repo.AppointmentRepo;
import com.scheduler.appointment.service.AppointmentService;
import com.scheduler.appointment.service.AppointmentSlotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	AppointmentSlotService appointmentSlotService;

	@Autowired
	AppointmentRepo appointmentRepo;
	@Autowired
	AppointmentPoRepo appointmentPoRepo;

	@Autowired
	private KafkaTemplate<String, Appointment> kafkaTemplate;

	@Value("${kafka.topic}")
	private String topicName;

	@SuppressWarnings("unused")
	@Override
	public Object createAppointment(AppointmentDto appointmentDto) throws BusinessException {
		List<AppointmentSlot> apptSlotList = appointmentSlotService.getAvailableSlots(appointmentDto.getDcNumber());
		Appointment appointment = new Appointment();
		List<PurchaseOrderDto> poList = appointmentDto.getPos();
		int totalQty = poList.stream().map(qty -> qty.getQty()).reduce(0, Integer::sum);
		for (AppointmentSlot slot : apptSlotList) {
			int usedTruckCount = appointmentRepo.getCountBySlotId(slot.getId());
			if (usedTruckCount < slot.getMaxTruckCount()) {
				appointment.setAppointmentSlotId(slot.getId());
				appointment.setDcNumber(appointmentDto.getDcNumber());
				appointment.setAppointmentDate(appointmentDto.getAppointmentDate());
				appointment.setTruckNumber(appointmentDto.getTruckNumber());
				appointment.setCreatedTimeStamp(new Date());
				appointment.setQty(totalQty);
				appointment.setAppointmentStatusId(AppointmentStatus.SCHEDULED.getStatusId());
				appointmentRepo.save(appointment);
				for (PurchaseOrderDto po : appointmentDto.getPos()) {
					AppointmentPo apptPo = new AppointmentPo();
					AppoinmentPoPk apptPoId = new AppoinmentPoPk();
					apptPoId.setAppointmentId(appointment.getAppiointmentId());
					apptPoId.setPoNumber(po.getPoNbr());
					apptPo.setApptPoId(apptPoId);
					appointmentPoRepo.save(apptPo);
				}
				sendAppointmentInfoToDownStream(appointment);
			}
		}
		if (null!=appointment.getAppiointmentId()) {
			return appointment;
		} else {
			throw new BusinessException("Maximum Truck count reached");
		}
	}

	void sendAppointmentInfoToDownStream(Appointment appointment) {
		Message<Appointment> message = MessageBuilder.withPayload(appointment).build();
		StringBuilder sb = new StringBuilder();
		final ListenableFuture<SendResult<String, Appointment>> producer = kafkaTemplate.send(topicName, appointment);
		producer.addCallback(new ListenableFutureCallback<SendResult<String, Appointment>>() {
			@Override
			public void onFailure(Throwable throwable) {
				sb.append(throwable.getMessage());
				throwable.printStackTrace();
			}

			@Override
			public void onSuccess(SendResult<String, Appointment> result) {
				System.out.println("Data successfully registered with Kafka Topic..");
				System.out.println("Partition -> " + result.getRecordMetadata().partition());
				System.out.println("Offset -> " + result.getRecordMetadata().offset());
			}
		});

	}

	@Override
	public Object getAllAppointment() {
		return appointmentRepo.findAll();
	}

	@Override
	public void deleteAppointment(Integer id) throws BusinessException {
		List<AppointmentPo> appointmentPos = appointmentPoRepo.getAppointmentPoById(id);
		AppoinmentPoPk appoinmentPoPk = new AppoinmentPoPk();
		if (null != appointmentPos) {
			for (AppointmentPo po : appointmentPos) {
				appoinmentPoPk.setAppointmentId(po.getApptPoId().getAppointmentId());
				appoinmentPoPk.setPoNumber(po.getApptPoId().getPoNumber());
			}
			appointmentRepo.deleteById(id);
			appointmentPoRepo.deleteById(appoinmentPoPk);
		} else {
			throw new BusinessException("Appointment Id not found");
		}
	}

	@Transactional
	@Override
	public void updateAppointment(AppointmentDto appointmentDto, int id) {
		List<PurchaseOrderDto> poList = appointmentDto.getPos();
		appointmentDto.setAppointmentStatusId(AppointmentStatus.MODIFIED.getStatusId());
		int totalQty = poList.stream().map(qty -> qty.getQty()).reduce(0, Integer::sum);
		appointmentRepo.updateAppointment(totalQty, id);
	}

}
