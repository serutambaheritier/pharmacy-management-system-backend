package com.auca.pharmacymanagementsystem.repository;

import com.auca.pharmacymanagementsystem.entity.DrugOrder;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrugOrderRepository extends JpaRepository<DrugOrder, UUID> {
}