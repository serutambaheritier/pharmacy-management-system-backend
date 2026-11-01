package com.auca.pharmacymanagementsystem.controller;

import com.auca.pharmacymanagementsystem.entity.DrugClass;
import com.auca.pharmacymanagementsystem.service.DrugClassService;
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
@RequestMapping("/api/drug-classes")
public class DrugClassController {
	private final DrugClassService drugClassService;

	public DrugClassController(DrugClassService drugClassService) {
		this.drugClassService = drugClassService;
	}

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody DrugClass drugClass) {
		return ResponseEntity.status(HttpStatus.CREATED).body(drugClassService.save(drugClass));
	}

	@GetMapping("/all")
	public List<DrugClass> findAll() {
		return drugClassService.findAll();
	}

	@GetMapping("/unused")
	public List<DrugClass> findUnusedDrugClasses() {
		return drugClassService.findUnusedDrugClasses();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable UUID id) {
		var drugClass = drugClassService.findById(id);
		if (drugClass.isPresent()) {
			return ResponseEntity.ok(drugClass.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The drug class with that id does not exist");
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody DrugClass drugClass) {
		String message = drugClassService.update(id, drugClass);
		if (message.equals("The drug class with that id does not exist")) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
		} else {
			return ResponseEntity.ok(message);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable UUID id) {
		String message = drugClassService.delete(id);
		if (message.equals("The drug class with that id does not exist")) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
		} else {
			return ResponseEntity.ok(message);
		}
	}
}