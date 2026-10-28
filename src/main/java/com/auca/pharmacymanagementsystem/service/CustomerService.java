package com.auca.pharmacymanagementsystem.service;

import com.auca.pharmacymanagementsystem.entity.Customer;
import com.auca.pharmacymanagementsystem.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public String save(Customer customer) {
		customerRepository.save(customer);
		return "Customer saved successfully";
	}

	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	public Optional<Customer> findById(UUID id) {
		return customerRepository.findById(id);
	}

	public List<Customer> findByLastName(String lastName) {
		return customerRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
	}

	public List<Customer> findByPharmacistId(UUID pharmacistId) {
		return customerRepository.findDistinctByDrugOrderPharmacistId(pharmacistId);
	}

	public String update(UUID id, Customer updatedCustomer) {
		return customerRepository.findById(id).map(customer -> {
			customer.setFirstName(updatedCustomer.getFirstName());
			customer.setLastName(updatedCustomer.getLastName());
			customer.setCustomerNumber(updatedCustomer.getCustomerNumber());
			customer.setPhone(updatedCustomer.getPhone());
			customer.setInsurance(updatedCustomer.getInsurance());
			customerRepository.save(customer);
			return "Customer updated successfully";
		}).orElse("The customer with that id does not exist");
	}

	public String delete(UUID id) {
		if (customerRepository.findById(id).isEmpty()) {
			return "The customer with that id does not exist";
		}
		customerRepository.deleteById(id);
		return "Customer deleted successfully";
	}
}