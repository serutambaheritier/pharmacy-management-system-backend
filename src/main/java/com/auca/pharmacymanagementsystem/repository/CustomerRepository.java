package com.auca.pharmacymanagementsystem.repository;

import com.auca.pharmacymanagementsystem.entity.Customer;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}