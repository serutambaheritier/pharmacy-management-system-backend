package com.auca.pharmacymanagementsystem.controller;

import com.auca.pharmacymanagementsystem.entity.Counter;
import com.auca.pharmacymanagementsystem.service.CounterService;
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
@RequestMapping("/api/counters")
public class CounterController {
	private final CounterService counterService;

	public CounterController(CounterService counterService) {
		this.counterService = counterService;
	}

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody Counter counter) {
		return ResponseEntity.status(HttpStatus.CREATED).body(counterService.save(counter));
	}

	@GetMapping("/all")
	public List<Counter> findAll() {
		return counterService.findAll();
	}

	@GetMapping("/free")
	public List<Counter> findFreeCounters() {
		return counterService.findFreeCounters();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable UUID id) {
		var counter = counterService.findById(id);
		if (counter.isPresent()) {
			return ResponseEntity.ok(counter.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The counter with that id does not exist");
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Counter counter) {
		String message = counterService.update(id, counter);
		if (message.equals("The counter with that id does not exist")) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
		} else {
			return ResponseEntity.ok(message);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable UUID id) {
		String message = counterService.delete(id);
		if (message.equals("The counter with that id does not exist")) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
		} else {
			return ResponseEntity.ok(message);
		}
	}
}