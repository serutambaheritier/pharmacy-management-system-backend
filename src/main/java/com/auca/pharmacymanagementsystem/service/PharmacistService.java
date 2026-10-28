package com.auca.pharmacymanagementsystem.service;

import com.auca.pharmacymanagementsystem.entity.Pharmacist;
import com.auca.pharmacymanagementsystem.repository.PharmacistRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PharmacistService {
	private final PharmacistRepository pharmacistRepository;

	public PharmacistService(PharmacistRepository pharmacistRepository) {
		this.pharmacistRepository = pharmacistRepository;
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