package com.auca.pharmacymanagementsystem.repository;

import com.auca.pharmacymanagementsystem.entity.Customer;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
	List<Customer> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

	@Query("SELECT DISTINCT o.customer FROM DrugOrder o WHERE o.pharmacist.id = :pharmacistId")
	List<Customer> findDistinctByDrugOrderPharmacistId(@Param("pharmacistId") UUID pharmacistId);
}