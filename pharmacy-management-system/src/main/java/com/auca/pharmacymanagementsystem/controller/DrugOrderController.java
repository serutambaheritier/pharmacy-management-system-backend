package com.auca.pharmacymanagementsystem.controller;

import com.auca.pharmacymanagementsystem.entity.DrugOrder;
import com.auca.pharmacymanagementsystem.entity.OrderStatus;
import com.auca.pharmacymanagementsystem.service.DrugOrderService;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		String message = drugOrderService.save(drugOrder);
		if (message.equals("Drug order date cannot be in the past")) {
			return ResponseEntity.badRequest().body(message);
		}
		if (message.equals("Pharmacist already prepares an order on that date")) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	@GetMapping("/all")
	public List<DrugOrder> findAll() {
		return drugOrderService.findAll();
	}

	@GetMapping("/page")
	public Page<DrugOrder> findPage(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "orderDate,desc") String sort) {
		String[] sortParts = sort.split(",", 2);
		Sort.Direction direction = sortParts.length > 1
				? Sort.Direction.fromString(sortParts[1])
				: Sort.Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortParts[0]));
		return drugOrderService.findPage(pageable);
	}

	@GetMapping("/by-status")
	public List<DrugOrder> findByStatus(@RequestParam OrderStatus status) {
		return drugOrderService.findByStatus(status);
	}

	@GetMapping("/between")
	public List<DrugOrder> findByDateRange(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
		return drugOrderService.findByDateRange(start, end);
	}

	@GetMapping("/stats/by-status")
	public List<Object[]> countDrugOrdersByStatus() {
		return drugOrderService.countDrugOrdersByStatus();
	}

	@PatchMapping("/cancel-day")
	public ResponseEntity<String> cancelOrdersForPharmacistOnDate(
			@RequestParam UUID pharmacistId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		int cancelledCount = drugOrderService.cancelOrdersForPharmacistOnDate(pharmacistId, date);
		return ResponseEntity.ok(cancelledCount + " drug orders cancelled");
	}

	@PatchMapping("/{id}/status")
	public ResponseEntity<String> updateStatus(
			@PathVariable UUID id, @RequestParam OrderStatus status) {
		String message = drugOrderService.updateStatus(id, status);
		if (message.equals("The drug order with that id does not exist")) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
		}
		if (message.startsWith("Cannot change status from ")) {
			return ResponseEntity.badRequest().body(message);
		}
		return ResponseEntity.ok(message);
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
		}
		if (message.equals("That drug order has dispensed items, delete them first")) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
		}
		return ResponseEntity.ok(message);
	}
}