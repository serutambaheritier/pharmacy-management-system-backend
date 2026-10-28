package com.auca.pharmacymanagementsystem.service;

import com.auca.pharmacymanagementsystem.entity.Counter;
import com.auca.pharmacymanagementsystem.repository.CounterRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CounterService {
	private final CounterRepository counterRepository;

	public CounterService(CounterRepository counterRepository) {
		this.counterRepository = counterRepository;
	}

	public String save(Counter counter) {
		counterRepository.save(counter);
		return "Counter saved successfully";
	}

	public List<Counter> findAll() {
		return counterRepository.findAll();
	}

	public List<Counter> findFreeCounters() {
		return counterRepository.findFreeCounters();
	}

	public Optional<Counter> findById(UUID id) {
		return counterRepository.findById(id);
	}

	public String update(UUID id, Counter updatedCounter) {
		return counterRepository.findById(id).map(counter -> {
			counter.setCounterNumber(updatedCounter.getCounterNumber());
			counter.setName(updatedCounter.getName());
			counterRepository.save(counter);
			return "Counter updated successfully";
		}).orElse("The counter with that id does not exist");
	}

	public String delete(UUID id) {
		if (counterRepository.findById(id).isEmpty()) {
			return "The counter with that id does not exist";
		}
		counterRepository.deleteById(id);
		return "Counter deleted successfully";
	}
}