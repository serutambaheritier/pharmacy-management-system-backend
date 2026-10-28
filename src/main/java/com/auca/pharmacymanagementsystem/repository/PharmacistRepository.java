package com.auca.pharmacymanagementsystem.repository;

import com.auca.pharmacymanagementsystem.entity.Pharmacist;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PharmacistRepository extends JpaRepository<Pharmacist, UUID> {
	@Query("SELECT DISTINCT p FROM Pharmacist p JOIN p.drugClasses dc WHERE LOWER(dc.name) = LOWER(:name)")
	List<Pharmacist> findByDrugClassNameIgnoreCase(@Param("name") String name);
}