package com.auca.pharmacymanagementsystem.repository;

import com.auca.pharmacymanagementsystem.entity.Counter;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CounterRepository extends JpaRepository<Counter, UUID> {
	@Query("SELECT c FROM Counter c WHERE NOT EXISTS (SELECT p.id FROM Pharmacist p WHERE p.counter = c) ORDER BY c.counterNumber ASC")
	List<Counter> findFreeCounters();
}