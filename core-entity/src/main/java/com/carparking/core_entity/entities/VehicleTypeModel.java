package com.carparking.core_entity.entities;

import javax.persistence.*;

@Entity
@Table(name = "vehicle_type")
public class VehicleTypeModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "vehicle_type_code")
	private String vehicleTypeCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVehicleTypeCode() {
		return vehicleTypeCode;
	}

	public void setVehicleTypeCode(String vehicleTypeCode) {
		this.vehicleTypeCode = vehicleTypeCode;
	}

}
