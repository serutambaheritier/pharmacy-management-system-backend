package com.auca.pharmacymanagementsystem.repository;

import com.auca.pharmacymanagementsystem.entity.DrugOrder;
import com.auca.pharmacymanagementsystem.entity.OrderStatus;
import com.auca.pharmacymanagementsystem.entity.Pharmacist;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface DrugOrderRepository extends JpaRepository<DrugOrder, UUID> {
	List<DrugOrder> findByStatusOrderByOrderDateAsc(OrderStatus status);
	List<DrugOrder> findByOrderDateBetweenOrderByOrderDateAsc(LocalDate start, LocalDate end);
	long countByOrderDate(LocalDate orderDate);
	long countByStatus(OrderStatus status);
	boolean existsByPharmacistAndOrderDateAndStatusNot(
			Pharmacist pharmacist, LocalDate orderDate, OrderStatus status);

	@Query("SELECT d.status, COUNT(d) FROM DrugOrder d GROUP BY d.status")
	List<Object[]> countDrugOrdersByStatus();

	@Modifying
	@Transactional
	@Query("UPDATE DrugOrder d SET d.status = :cancelled "
			+ "WHERE d.pharmacist.id = :pharmacistId AND d.orderDate = :date "
			+ "AND d.status <> :dispensed")
	int cancelOrdersForPharmacistOnDate(
			@Param("pharmacistId") UUID pharmacistId,
			@Param("date") LocalDate date,
			@Param("cancelled") OrderStatus cancelled,
			@Param("dispensed") OrderStatus dispensed);
}