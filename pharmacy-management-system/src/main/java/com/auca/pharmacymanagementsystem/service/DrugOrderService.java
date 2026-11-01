package com.auca.pharmacymanagementsystem.service;

import com.auca.pharmacymanagementsystem.entity.DrugOrder;
import com.auca.pharmacymanagementsystem.entity.OrderStatus;
import com.auca.pharmacymanagementsystem.repository.DispensedItemRepository;
import com.auca.pharmacymanagementsystem.repository.DrugOrderRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DrugOrderService {
	private final DrugOrderRepository drugOrderRepository;
	private final DispensedItemRepository dispensedItemRepository;

	public DrugOrderService(
			DrugOrderRepository drugOrderRepository,
			DispensedItemRepository dispensedItemRepository) {
		this.drugOrderRepository = drugOrderRepository;
		this.dispensedItemRepository = dispensedItemRepository;
	}

	public String save(DrugOrder drugOrder) {
		if (drugOrder.getOrderDate().isBefore(LocalDate.now())) {
			return "Drug order date cannot be in the past";
		}
		if (drugOrderRepository.existsByPharmacistAndOrderDateAndStatusNot(
				drugOrder.getPharmacist(), drugOrder.getOrderDate(), OrderStatus.CANCELLED)) {
			return "Pharmacist already prepares an order on that date";
		}
		drugOrder.setStatus(OrderStatus.PLACED);
		drugOrderRepository.save(drugOrder);
		return "Drug order saved successfully";
	}

	public List<DrugOrder> findAll() {
		return drugOrderRepository.findAll();
	}

	public Page<DrugOrder> findPage(Pageable pageable) {
		return drugOrderRepository.findAll(pageable);
	}

	public Optional<DrugOrder> findById(UUID id) {
		return drugOrderRepository.findById(id);
	}

	public List<DrugOrder> findByStatus(OrderStatus status) {
		return drugOrderRepository.findByStatusOrderByOrderDateAsc(status);
	}

	public List<DrugOrder> findByDateRange(LocalDate start, LocalDate end) {
		return drugOrderRepository.findByOrderDateBetweenOrderByOrderDateAsc(start, end);
	}

	public List<Object[]> countDrugOrdersByStatus() {
		return drugOrderRepository.countDrugOrdersByStatus();
	}

	public int cancelOrdersForPharmacistOnDate(UUID pharmacistId, LocalDate date) {
		return drugOrderRepository.cancelOrdersForPharmacistOnDate(
				pharmacistId, date, OrderStatus.CANCELLED, OrderStatus.DISPENSED);
	}

	public String updateStatus(UUID id, OrderStatus newStatus) {
		Optional<DrugOrder> orderOptional = drugOrderRepository.findById(id);
		if (orderOptional.isEmpty()) {
			return "The drug order with that id does not exist";
		}
		DrugOrder drugOrder = orderOptional.get();
		OrderStatus currentStatus = drugOrder.getStatus();
		boolean allowed = (currentStatus == OrderStatus.PLACED
				&& (newStatus == OrderStatus.PREPARED || newStatus == OrderStatus.CANCELLED))
				|| (currentStatus == OrderStatus.PREPARED
						&& (newStatus == OrderStatus.DISPENSED || newStatus == OrderStatus.CANCELLED));
		if (!allowed) {
			return "Cannot change status from " + currentStatus + " to " + newStatus;
		}
		drugOrder.setStatus(newStatus);
		drugOrderRepository.save(drugOrder);
		return "Drug order status updated successfully";
	}

	public String update(UUID id, DrugOrder updatedDrugOrder) {
		return drugOrderRepository.findById(id).map(drugOrder -> {
			drugOrder.setOrderDate(updatedDrugOrder.getOrderDate());
			drugOrder.setPrescriptionRef(updatedDrugOrder.getPrescriptionRef());
			drugOrder.setTotalAmount(updatedDrugOrder.getTotalAmount());
			drugOrder.setCustomer(updatedDrugOrder.getCustomer());
			drugOrder.setPharmacist(updatedDrugOrder.getPharmacist());
			drugOrderRepository.save(drugOrder);
			return "Drug order updated successfully";
		}).orElse("The drug order with that id does not exist");
	}

	public String delete(UUID id) {
		Optional<DrugOrder> drugOrderOptional = drugOrderRepository.findById(id);
		if (drugOrderOptional.isEmpty()) {
			return "The drug order with that id does not exist";
		}
		if (dispensedItemRepository.existsByDrugOrder(drugOrderOptional.get())) {
			return "That drug order has dispensed items, delete them first";
		}
		drugOrderRepository.deleteById(id);
		return "Drug order deleted successfully";
	}
}