package com.auca.pharmacymanagementsystem.controller;

import com.auca.pharmacymanagementsystem.entity.DrugOrder;
import com.auca.pharmacymanagementsystem.service.DrugOrderService;
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
@RequestMapping("/api/drug-orders")
public class DrugOrderController {
	private final DrugOrderService drugOrderService;

	public DrugOrderController(DrugOrderService drugOrderService) {
		this.drugOrderService = drugOrderService;
	}

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody DrugOrder drugOrder) {
		return ResponseEntity.status(HttpStatus.CREATED).body(drugOrderService.save(drugOrder));
	}

	@GetMapping("/all")
	public List<DrugOrder> findAll() {
		return drugOrderService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable UUID id) {
		var drugOrder = drugOrderService.findById(id);
		if (drugOrder.isPresent()) {
			return ResponseEntity.ok(drugOrder.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The drug order with that id does not exist");
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody DrugOrder drugOrder) {
		String message = drugOrderService.update(id, drugOrder);
		if (message.equals("The drug order with that id does not exist")) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
		} else {
			return ResponseEntity.ok(message);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable UUID id) {
		String message = drugOrderService.delete(id);
		if (message.equals("The drug order with that id does not exist")) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
		} else {
			return ResponseEntity.ok(message);
		}
	}
}