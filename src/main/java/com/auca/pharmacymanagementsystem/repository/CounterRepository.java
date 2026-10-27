package com.auca.pharmacymanagementsystem.repository;

import com.auca.pharmacymanagementsystem.entity.Counter;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounterRepository extends JpaRepository<Counter, UUID> {
}