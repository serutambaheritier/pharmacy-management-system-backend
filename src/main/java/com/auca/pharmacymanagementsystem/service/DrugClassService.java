package com.auca.pharmacymanagementsystem.service;

import com.auca.pharmacymanagementsystem.entity.DrugClass;
import com.auca.pharmacymanagementsystem.repository.DrugClassRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class DrugClassService {
	private final DrugClassRepository drugClassRepository;

	public DrugClassService(DrugClassRepository drugClassRepository) {
		this.drugClassRepository = drugClassRepository;
	}

	public String save(DrugClass drugClass) {
		drugClassRepository.save(drugClass);
		return "Drug class saved successfully";
	}

	public List<DrugClass> findAll() {
		return drugClassRepository.findAll();
	}

	public Optional<DrugClass> findById(UUID id) {
		return drugClassRepository.findById(id);
	}

	public String update(UUID id, DrugClass updatedDrugClass) {
		return drugClassRepository.findById(id).map(drugClass -> {
			drugClass.setName(updatedDrugClass.getName());
			drugClass.setPharmacists(updatedDrugClass.getPharmacists());
			drugClassRepository.save(drugClass);
			return "Drug class updated successfully";
		}).orElse("The drug class with that id does not exist");
	}

	public String delete(UUID id) {
		if (drugClassRepository.findById(id).isEmpty()) {
			return "The drug class with that id does not exist";
		}
		drugClassRepository.deleteById(id);
		return "Drug class deleted successfully";
	}
}