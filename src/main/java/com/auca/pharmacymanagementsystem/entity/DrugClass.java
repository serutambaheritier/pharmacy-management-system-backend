package com.auca.pharmacymanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "drug_class")
public class DrugClass {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String name;

	@JsonIgnore
	@ManyToMany(mappedBy = "drugClasses")
	private List<Pharmacist> pharmacists;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Pharmacist> getPharmacists() {
		return pharmacists;
	}

	public void setPharmacists(List<Pharmacist> pharmacists) {
		this.pharmacists = pharmacists;
	}
}