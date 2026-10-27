package com.auca.pharmacymanagementsystem.service;

import com.auca.pharmacymanagementsystem.entity.DrugOrder;
import com.auca.pharmacymanagementsystem.repository.DrugOrderRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class DrugOrderService {
	private final DrugOrderRepository drugOrderRepository;

	public DrugOrderService(DrugOrderRepository drugOrderRepository) {
		this.drugOrderRepository = drugOrderRepository;
	}

	public String save(DrugOrder drugOrder) {
		drugOrderRepository.save(drugOrder);
		return "Drug order saved successfully";
	}

	public List<DrugOrder> findAll() {
		return drugOrderRepository.findAll();
	}

	public Optional<DrugOrder> findById(UUID id) {
		return drugOrderRepository.findById(id);
	}

	public String update(UUID id, DrugOrder updatedDrugOrder) {
		return drugOrderRepository.findById(id).map(drugOrder -> {
			drugOrder.setOrderDate(updatedDrugOrder.getOrderDate());
			drugOrder.setStatus(updatedDrugOrder.getStatus());
			drugOrder.setPrescriptionRef(updatedDrugOrder.getPrescriptionRef());
			drugOrder.setTotalAmount(updatedDrugOrder.getTotalAmount());
			drugOrder.setCustomer(updatedDrugOrder.getCustomer());
			drugOrder.setPharmacist(updatedDrugOrder.getPharmacist());
			drugOrderRepository.save(drugOrder);
			return "Drug order updated successfully";
		}).orElse("The drug order with that id does not exist");
	}

	public String delete(UUID id) {
		if (drugOrderRepository.findById(id).isEmpty()) {
			return "The drug order with that id does not exist";
		}
		drugOrderRepository.deleteById(id);
		return "Drug order deleted successfully";
	}
}