package com.auca.pharmacymanagementsystem.controller;

import com.auca.pharmacymanagementsystem.entity.Pharmacist;
import com.auca.pharmacymanagementsystem.service.PharmacistService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pharmacists")
public class PharmacistController {
	private final PharmacistService pharmacistService;

	public PharmacistController(PharmacistService pharmacistService) {
		this.pharmacistService = pharmacistService;
	}

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody Pharmacist pharmacist) {
		return ResponseEntity.status(HttpStatus.CREATED).body(pharmacistService.save(pharmacist));
	}

	@GetMapping("/all")
	public List<Pharmacist> findAll() {
		return pharmacistService.findAll();
	}

	@GetMapping("/by-drug-class")
	public List<Pharmacist> findByDrugClassName(@RequestParam String name) {
		return pharmacistService.findByDrugClassName(name);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable UUID id) {
		var pharmacist = pharmacistService.findById(id);
		if (pharmacist.isPresent()) {
			return ResponseEntity.ok(pharmacist.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The pharmacist with that id does not exist");
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Pharmacist pharmacist) {
		String message = pharmacistService.update(id, pharmacist);
		if (message.equals("The pharmacist with that id does not exist")) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
		} else {
			return ResponseEntity.ok(message);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable UUID id) {
		String message = pharmacistService.delete(id);
		if (message.equals("The pharmacist with that id does not exist")) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
		} else {
			return ResponseEntity.ok(message);
		}
	}
}