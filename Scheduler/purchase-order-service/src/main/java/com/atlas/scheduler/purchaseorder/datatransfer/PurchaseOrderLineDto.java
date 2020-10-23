package com.atlas.scheduler.purchaseorder.datatransfer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PurchaseOrderLineDto {
	 private Integer poLineNbr;
	 private Integer upc;
	 private String itemDesc;
	 private Integer quantity;

}
