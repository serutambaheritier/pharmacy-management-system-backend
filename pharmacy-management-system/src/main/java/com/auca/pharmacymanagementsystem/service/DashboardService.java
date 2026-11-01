package com.auca.pharmacymanagementsystem.service;

import com.auca.pharmacymanagementsystem.entity.OrderStatus;
import com.auca.pharmacymanagementsystem.repository.CounterRepository;
import com.auca.pharmacymanagementsystem.repository.CustomerRepository;
import com.auca.pharmacymanagementsystem.repository.DispensedItemRepository;
import com.auca.pharmacymanagementsystem.repository.DrugOrderRepository;
import com.auca.pharmacymanagementsystem.repository.PharmacistRepository;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
	private final CustomerRepository customerRepository;
	private final PharmacistRepository pharmacistRepository;
	private final CounterRepository counterRepository;
	private final DrugOrderRepository drugOrderRepository;
	private final DispensedItemRepository dispensedItemRepository;

	public DashboardService(
			CustomerRepository customerRepository,
			PharmacistRepository pharmacistRepository,
			CounterRepository counterRepository,
			DrugOrderRepository drugOrderRepository,
			DispensedItemRepository dispensedItemRepository) {
		this.customerRepository = customerRepository;
		this.pharmacistRepository = pharmacistRepository;
		this.counterRepository = counterRepository;
		this.drugOrderRepository = drugOrderRepository;
		this.dispensedItemRepository = dispensedItemRepository;
	}

	public Map<String, Long> getSummary() {
		Map<String, Long> summary = new LinkedHashMap<>();
		summary.put("customers", customerRepository.count());
		summary.put("pharmacists", pharmacistRepository.count());
		summary.put("freeCounters", (long) counterRepository.findFreeCounters().size());
		summary.put("ordersToday", drugOrderRepository.countByOrderDate(LocalDate.now()));
		summary.put("placed", drugOrderRepository.countByStatus(OrderStatus.PLACED));
		summary.put("dispensedItems", dispensedItemRepository.count());
		return summary;
	}
}