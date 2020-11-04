package com.atlas.scheduler.purchaseorder.datatransfer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseOrderLineDto {
	 private Integer poLineNbr;
	 private Integer upc;
	 private String itemDesc;
	 private Integer quantity;

}
