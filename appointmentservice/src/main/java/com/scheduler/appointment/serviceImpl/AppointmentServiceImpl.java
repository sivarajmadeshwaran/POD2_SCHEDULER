package com.scheduler.appointment.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	AppointmentSlotService appointmentSlotService;

	@Autowired
	AppointmentRepo appointmentRepo;
	@Autowired
	AppointmentPoRepo appointmentPoRepo;

	@Override
	public Object createAppointment(AppointmentDto appointmentDto) throws BusinessException {
		List<AppointmentSlot> apptSlotList = appointmentSlotService.getAvailableSlots(appointmentDto.getDcNumber());
		Appointment appointment = new Appointment();
		List<PurchaseOrderDto> poList = appointmentDto.getPos();
		Iterable<AppointmentPo> itr = appointmentPoRepo.findAll();
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
			}

		}
		return appointment;
	}

	@Override
	public Object getAllAppointment() {
		return appointmentRepo.findAll();
	}

	@Transactional
	@Override
	public void deleteAppointment(Integer id) {
		appointmentRepo.deleteAppointment(id,AppointmentStatus.DELETED.getStatusId());
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
