package com.auca.pharmacymanagementsystem.repository;

import com.auca.pharmacymanagementsystem.entity.DrugOrder;
import com.auca.pharmacymanagementsystem.entity.OrderStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrugOrderRepository extends JpaRepository<DrugOrder, UUID> {
	List<DrugOrder> findByStatusOrderByOrderDateAsc(OrderStatus status);
	List<DrugOrder> findByOrderDateBetweenOrderByOrderDateAsc(LocalDate start, LocalDate end);
}