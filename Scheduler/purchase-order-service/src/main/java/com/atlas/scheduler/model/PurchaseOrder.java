package com.atlas.scheduler.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PurchaseOrder {
	
	private Integer poNbr;
	private Integer vendorNbr;
	private Date poDate;
	private String address;
	
	@JacksonXmlElementWrapper(localName = "poLines")
	private List<PurchaseOrderLine> poLine;

}
