package com.atlas.scheduler.purchaseorder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PurchaseOrderLine {
	 private Integer poLineNbr;
	 private Integer upc;
	 private String itemDesc;
	 private Integer quantity;

}
