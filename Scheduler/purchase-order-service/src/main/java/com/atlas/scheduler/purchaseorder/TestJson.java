package com.atlas.scheduler.purchaseorder;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class TestJson {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 PurchaseOrder order = new PurchaseOrder();
		 PurchaseOrderLine line =new PurchaseOrderLine();
		 line.setItemDesc("Test Item");
		 line.setPoLineNbr(1);
		 line.setQuantity(10);
		 line.setUpc(100012);
		 
		 PurchaseOrderLine line1 =new PurchaseOrderLine();
		 line.setItemDesc("Test Item");
		 line.setPoLineNbr(2);
		 line.setQuantity(10);
		 line.setUpc(100011);
		 
		 List<PurchaseOrderLine> lines=new ArrayList<>();
		 lines.add(line);
		 order.setAddress("Erode");
		 order.setPoDate(new Date());
		 order.setPoNbr(123456);
		 order.setPoLine(lines);
		 order.setVendorNbr(1);
		 
		 List<Integer> ins=Arrays.asList(1,2,3,45);
		 
		 
		 JSONObject obj=new JSONObject(ins);
		 
		 StringWriter sw = new StringWriter();
		/*	JAXBContext jaxbContext = JAXBContext.newInstance(PurchaseOrder.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			jaxbMarshaller.marshal(order,sw);*/
			
			XmlMapper mapper =new XmlMapper();
			String xmlString = null;
			try {
				xmlString = mapper.writeValueAsString(order);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 System.out.println(xmlString);
	}

}
