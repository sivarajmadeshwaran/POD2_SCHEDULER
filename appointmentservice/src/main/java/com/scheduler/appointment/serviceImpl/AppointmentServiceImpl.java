package com.scheduler.appointment.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
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

import lombok.extern.slf4j.Slf4j;

import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * <h1>Appointment service implementation</h1>
 *
 */
@Service
@Slf4j
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

	@Autowired
	private PurchaseOrderFeignClient purchaseOrderFeign;

	/**
	 * <h1>Creating appointment /h1>
	 * 
	 * @param appointmentDto
	 * @return appointment object
	 */
	@SuppressWarnings("unused")
	@Override
	public Object createAppointment(AppointmentDto appointmentDto) throws BusinessException {
		List<AppointmentSlot> apptSlotList = appointmentSlotService.getAvailableSlots(appointmentDto.getDcNumber());
		Appointment appointment = new Appointment();
		List<PurchaseOrderDto> poList = appointmentDto.getPos();
		int totalQty = poList.stream().map(qty -> qty.getQty()).reduce(0, Integer::sum);
		if (checkPoAvailability(appointmentDto.getPos())) {
			for (AppointmentSlot slot : apptSlotList) {
				int usedTruckCount = appointmentRepo.getCountBySlotId(slot.getId());
				if (usedTruckCount < slot.getMaxTruckCount()) {
					appointment.setAppointmentSlotId(slot.getId());
					appointment.setDcNumber(appointmentDto.getDcNumber());
					appointment.setAppointmentDate(appointmentDto.getAppointmentDate());
					appointment.setTruckNumber(appointmentDto.getTruckNumber());
					appointment.setCreatedTimeStamp(new Date());
					appointment.setAppointmentStatusId(AppointmentStatus.SCHEDULED.getStatusId());
					appointment.setQty(totalQty);
					appointmentRepo.save(appointment);
					for (PurchaseOrderDto po : appointmentDto.getPos()) {
						AppointmentPo apptPo = new AppointmentPo();
						AppoinmentPoPk apptPoId = new AppoinmentPoPk();
						apptPoId.setAppointmentId(appointment.getAppiointmentId());
						apptPoId.setPoNumber(po.getPoNbr());
						apptPo.setApptPoId(apptPoId);
						appointmentPoRepo.save(apptPo);
					}

				}
			}
		} else {
			throw new BusinessException("PO is not valid");
		}
		if (null != appointment.getAppiointmentId()) {
			sendAppointmentInfoToDownStream(appointment);
			return appointment;
		} else {
			throw new BusinessException("Maximum Truck count reached");
		}
	}

	/**
	 * <h1>Sending appointment to down stream</h1>
	 * 
	 * @param appointment
	 * 
	 */
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

	/**
	 * <h1>getting all appointment</h1>
	 * 
	 * @return appointment object
	 * 
	 */
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
				appointmentPoRepo.deleteById(appoinmentPoPk);
			}
			appointmentRepo.deleteById(id);

		} else {
			throw new BusinessException("Appointment Id not found");
		}
	}

	/**
	 * <h1>updating appointment by ID</h1>
	 * 
	 * @param id,appointmentDto
	 * 
	 */
	@Transactional
	@Override
	public void updateAppointment(AppointmentDto appointmentDto, int id) {
		List<PurchaseOrderDto> poList = appointmentDto.getPos();
		Appointment appointment = new Appointment();
		if (checkPoAvailability(appointmentDto.getPos())) {
			int totalQty = poList.stream().map(qty -> qty.getQty()).reduce(0, Integer::sum);
			appointment.setQty(totalQty);
			appointment.setAppointmentStatusId(AppointmentStatus.MODIFIED.getStatusId());
			appointmentRepo.updateAppointment(totalQty, id, appointmentDto.getAppointmentDate());
		}
	}

	private boolean checkPoAvailability(List<PurchaseOrderDto> poList) {
		if (poList != null && !poList.isEmpty()) {
			List<PurchaseOrderDto> purchaseOrderDtos = purchaseOrderFeign
					.getPoDetails(poList.stream().map(po -> po.getPoNbr()).collect(Collectors.toList()));
			List<PurchaseOrderDto> availablePos = purchaseOrderDtos.stream()
					.filter(po -> poList.stream().anyMatch(inputPo -> inputPo.getPoNbr() == po.getPoNbr()))
					.collect(Collectors.toList());
			return poList.size() == availablePos.size();
		}
		return false;
	}

}
