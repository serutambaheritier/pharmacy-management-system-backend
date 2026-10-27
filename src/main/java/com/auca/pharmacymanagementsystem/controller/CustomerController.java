package com.auca.pharmacymanagementsystem.controller;

import com.auca.pharmacymanagementsystem.entity.Customer;
import com.auca.pharmacymanagementsystem.service.CustomerService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody Customer customer) {
		return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customer));
	}

	@GetMapping("/all")
	public List<Customer> findAll() {
		return customerService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable UUID id) {
		var customer = customerService.findById(id);
		if (customer.isPresent()) {
			return ResponseEntity.ok(customer.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The customer with that id does not exist");
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Customer customer) {
		String message = customerService.update(id, customer);
		if (message.equals("The customer with that id does not exist")) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
		} else {
			return ResponseEntity.ok(message);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable UUID id) {
		String message = customerService.delete(id);
		if (message.equals("The customer with that id does not exist")) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
		} else {
			return ResponseEntity.ok(message);
		}
	}
}