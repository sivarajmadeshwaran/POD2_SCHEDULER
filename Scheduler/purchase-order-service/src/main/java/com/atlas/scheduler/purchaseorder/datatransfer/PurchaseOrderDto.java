package com.atlas.scheduler.purchaseorder.datatransfer;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JacksonXmlRootElement(localName="PurchaseOrder")
public class PurchaseOrderDto {
	
	private Integer poNbr;
	private Integer vendorNbr;
	private Date poDate;
	private String address;
	
	@JacksonXmlElementWrapper(localName = "poLines")
	private List<PurchaseOrderLineDto> poLine;
	
	private String failureReason;

}
