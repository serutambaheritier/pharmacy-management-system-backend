package com.auca.pharmacymanagementsystem.repository;

import com.auca.pharmacymanagementsystem.entity.DrugClass;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DrugClassRepository extends JpaRepository<DrugClass, UUID> {
	@Query("SELECT dc FROM DrugClass dc WHERE dc.pharmacists IS EMPTY")
	List<DrugClass> findUnusedDrugClasses();
}