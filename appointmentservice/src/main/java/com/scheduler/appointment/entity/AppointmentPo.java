package com.scheduler.appointment.entity;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="appointment_po")
public class AppointmentPo {



	@EmbeddedId
	private AppoinmentPoPk apptPoId;

	public AppoinmentPoPk getApptPoId() {
		return apptPoId;
	}

	public void setApptPoId(AppoinmentPoPk apptPoId) {
		this.apptPoId = apptPoId;
	}

}
