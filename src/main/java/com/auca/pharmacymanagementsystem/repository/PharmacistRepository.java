package com.auca.pharmacymanagementsystem.repository;

import com.auca.pharmacymanagementsystem.entity.Pharmacist;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacistRepository extends JpaRepository<Pharmacist, UUID> {
}