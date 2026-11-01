package com.auca.pharmacymanagementsystem.repository;

import com.auca.pharmacymanagementsystem.entity.DispensedItem;
import com.auca.pharmacymanagementsystem.entity.DrugOrder;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DispensedItemRepository extends JpaRepository<DispensedItem, UUID> {
	@Query("SELECT d FROM DispensedItem d WHERE d.drugOrder.customer.customerNumber = :customerNumber ORDER BY d.dispensedDate DESC")
	List<DispensedItem> findByCustomerNumber(@Param("customerNumber") String customerNumber);

	boolean existsByDrugOrder(DrugOrder drugOrder);
}