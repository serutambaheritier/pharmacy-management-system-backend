package com.auca.pharmacymanagementsystem.repository;

import com.auca.pharmacymanagementsystem.entity.DrugClass;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrugClassRepository extends JpaRepository<DrugClass, UUID> {
}