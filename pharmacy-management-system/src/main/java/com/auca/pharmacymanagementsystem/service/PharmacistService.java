package com.auca.pharmacymanagementsystem.service;

import com.auca.pharmacymanagementsystem.entity.Counter;
import com.auca.pharmacymanagementsystem.entity.DrugClass;
import com.auca.pharmacymanagementsystem.entity.Pharmacist;
import com.auca.pharmacymanagementsystem.repository.CounterRepository;
import com.auca.pharmacymanagementsystem.repository.DrugClassRepository;
import com.auca.pharmacymanagementsystem.repository.PharmacistRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PharmacistService {
	private final PharmacistRepository pharmacistRepository;
	private final DrugClassRepository drugClassRepository;
	private final CounterRepository counterRepository;

	public PharmacistService(
			PharmacistRepository pharmacistRepository,
			DrugClassRepository drugClassRepository,
			CounterRepository counterRepository) {
		this.pharmacistRepository = pharmacistRepository;
		this.drugClassRepository = drugClassRepository;
		this.counterRepository = counterRepository;
	}

	public String save(Pharmacist pharmacist) {
		pharmacistRepository.save(pharmacist);
		return "Pharmacist saved successfully";
	}

	public List<Pharmacist> findAll() {
		return pharmacistRepository.findAll();
	}

	public List<Pharmacist> findByDrugClassName(String name) {
		return pharmacistRepository.findByDrugClassNameIgnoreCase(name);
	}

	public Optional<Pharmacist> findById(UUID id) {
		return pharmacistRepository.findById(id);
	}

	public String addDrugClass(UUID pharmacistId, UUID drugClassId) {
		Optional<Pharmacist> pharmacistOptional = pharmacistRepository.findById(pharmacistId);
		if (pharmacistOptional.isEmpty()) {
			return "The pharmacist with that id does not exist";
		}
		Optional<DrugClass> drugClassOptional = drugClassRepository.findById(drugClassId);
		if (drugClassOptional.isEmpty()) {
			return "That drug class does not exist";
		}
		Pharmacist pharmacist = pharmacistOptional.get();
		DrugClass drugClass = drugClassOptional.get();
		if (pharmacist.getDrugClasses() == null) {
			pharmacist.setDrugClasses(new ArrayList<>());
		}
		if (pharmacist.getDrugClasses().stream()
				.anyMatch(existing -> existing.getId().equals(drugClass.getId()))) {
			return "The pharmacist already has that drug class";
		}
		pharmacist.getDrugClasses().add(drugClass);
		pharmacistRepository.save(pharmacist);
		return "Drug class added to the pharmacist";
	}

	public String changeCounter(UUID pharmacistId, int counterNumber) {
		Optional<Pharmacist> pharmacistOptional = pharmacistRepository.findById(pharmacistId);
		if (pharmacistOptional.isEmpty()) {
			return "The pharmacist with that id does not exist";
		}
		Optional<Counter> counterOptional = counterRepository.findByCounterNumber(counterNumber);
		if (counterOptional.isEmpty()) {
			return "The counter with that counterNumber does not exist";
		}
		Pharmacist pharmacist = pharmacistOptional.get();
		Counter counter = counterOptional.get();
		if (pharmacist.getCounter() != null
				&& pharmacist.getCounter().getId().equals(counter.getId())) {
			return "Counter changed successfully";
		}
		if (pharmacistRepository.existsByCounter(counter)) {
			return "That counter is taken by another pharmacist";
		}
		pharmacist.setCounter(counter);
		pharmacistRepository.save(pharmacist);
		return "Counter changed successfully";
	}

	public String update(UUID id, Pharmacist updatedPharmacist) {
		return pharmacistRepository.findById(id).map(pharmacist -> {
			pharmacist.setFirstName(updatedPharmacist.getFirstName());
			pharmacist.setLastName(updatedPharmacist.getLastName());
			pharmacist.setLicenseNumber(updatedPharmacist.getLicenseNumber());
			pharmacist.setCounter(updatedPharmacist.getCounter());
			pharmacist.setDrugClasses(updatedPharmacist.getDrugClasses());
			pharmacistRepository.save(pharmacist);
			return "Pharmacist updated successfully";
		}).orElse("The pharmacist with that id does not exist");
	}

	public String delete(UUID id) {
		if (pharmacistRepository.findById(id).isEmpty()) {
			return "The pharmacist with that id does not exist";
		}
		pharmacistRepository.deleteById(id);
		return "Pharmacist deleted successfully";
	}
}