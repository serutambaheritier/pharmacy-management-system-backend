package com.auca.pharmacymanagementsystem.controller;

import com.auca.pharmacymanagementsystem.entity.DispensedItem;
import com.auca.pharmacymanagementsystem.service.DispensedItemService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dispensed-items")
public class DispensedItemController {
	private final DispensedItemService dispensedItemService;

	public DispensedItemController(DispensedItemService dispensedItemService) {
		this.dispensedItemService = dispensedItemService;
	}

	@PostMapping("/save")
	public ResponseEntity<String> save(
			@RequestParam UUID drugOrderId, @RequestBody DispensedItem item) {
		DispensedItemService.SaveResult result = dispensedItemService.save(item, drugOrderId);
		if (result == DispensedItemService.SaveResult.DRUG_ORDER_NOT_FOUND) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("The drug order with that id does not exist");
		}
		if (result == DispensedItemService.SaveResult.ORDER_NOT_DISPENSED) {
			return ResponseEntity.badRequest().body("Dispensed items are only for DISPENSED drug orders");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Dispensed item saved successfully");
	}

	@GetMapping("/customer/{customerNumber}")
	public List<DispensedItem> findByCustomerNumber(@PathVariable String customerNumber) {
		return dispensedItemService.findByCustomerNumber(customerNumber);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable UUID id) {
		if (!dispensedItemService.delete(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("The dispensed item with that id does not exist");
		}
		return ResponseEntity.ok("Dispensed item deleted successfully");
	}
}