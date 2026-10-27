package com.auca.pharmacymanagementsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "counter")
public class Counter {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private int counterNumber;

	private String name;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public int getCounterNumber() {
		return counterNumber;
	}

	public void setCounterNumber(int counterNumber) {
		this.counterNumber = counterNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}