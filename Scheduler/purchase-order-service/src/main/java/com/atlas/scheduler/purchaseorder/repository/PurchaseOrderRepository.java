package com.atlas.scheduler.purchaseorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author sivaraj
 *  This is to Interact with purchase_order table via JPA 
 */
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {

}
