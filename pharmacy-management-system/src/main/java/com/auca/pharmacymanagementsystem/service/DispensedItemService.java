package com.auca.pharmacymanagementsystem.service;

import com.auca.pharmacymanagementsystem.entity.DispensedItem;
import com.auca.pharmacymanagementsystem.entity.DrugOrder;
import com.auca.pharmacymanagementsystem.entity.OrderStatus;
import com.auca.pharmacymanagementsystem.repository.DispensedItemRepository;
import com.auca.pharmacymanagementsystem.repository.DrugOrderRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class DispensedItemService {
	private final DispensedItemRepository dispensedItemRepository;
	private final DrugOrderRepository drugOrderRepository;

	public DispensedItemService(
			DispensedItemRepository dispensedItemRepository,
			DrugOrderRepository drugOrderRepository) {
		this.dispensedItemRepository = dispensedItemRepository;
		this.drugOrderRepository = drugOrderRepository;
	}

	public SaveResult save(DispensedItem item, UUID drugOrderId) {
		Optional<DrugOrder> drugOrderOptional = drugOrderRepository.findById(drugOrderId);
		if (drugOrderOptional.isEmpty()) {
			return SaveResult.DRUG_ORDER_NOT_FOUND;
		}
		DrugOrder drugOrder = drugOrderOptional.get();
		if (drugOrder.getStatus() != OrderStatus.DISPENSED) {
			return SaveResult.ORDER_NOT_DISPENSED;
		}
		item.setDrugOrder(drugOrder);
		item.setDispensedDate(LocalDate.now());
		dispensedItemRepository.save(item);
		return SaveResult.SAVED;
	}

	public List<DispensedItem> findByCustomerNumber(String customerNumber) {
		return dispensedItemRepository.findByCustomerNumber(customerNumber);
	}

	public boolean delete(UUID id) {
		if (dispensedItemRepository.findById(id).isEmpty()) {
			return false;
		}
		dispensedItemRepository.deleteById(id);
		return true;
	}

	public enum SaveResult {
		DRUG_ORDER_NOT_FOUND,
		ORDER_NOT_DISPENSED,
		SAVED
	}
}