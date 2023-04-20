package com.carparking.core_entity.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "vehicleCategory")
public class VehicleCategoryModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nameType")
	private String nameType;

	@Column(name = "unitPrice")
	private Float unitPrice;

	@Column(name = "unitPriceBlock")
	private Float unitPriceBlock;

	@Column(name = "unitPriceNight")
	private Float unitPriceNight;

	@Column(name = "unitPriceTurn")
	private Float unitPriceTurn;

	@Column(name = "landmarkDay")
	private String landmarkDay;

	@Column(name = "landmarkNight")
	private String landmarkNight;

	@Column(name = "timeBlock")
	private String timeBlock;

	@Column(name = "calculatingMoney")
	private String calculatingMoney;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "vehicle_type_id", referencedColumnName = "id", nullable = false)
	private VehicleTypeModel vehicleType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameType() {
		return nameType;
	}

	public void setNameType(String nameType) {
		this.nameType = nameType;
	}

	public Float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Float getUnitPriceBlock() {
		return unitPriceBlock;
	}

	public void setUnitPriceBlock(Float unitPriceBlock) {
		this.unitPriceBlock = unitPriceBlock;
	}

	public Float getUnitPriceNight() {
		return unitPriceNight;
	}

	public void setUnitPriceNight(Float unitPriceNight) {
		this.unitPriceNight = unitPriceNight;
	}

	public Float getUnitPriceTurn() {
		return unitPriceTurn;
	}

	public void setUnitPriceTurn(Float unitPriceTurn) {
		this.unitPriceTurn = unitPriceTurn;
	}

	public String getLandmarkDay() {
		return landmarkDay;
	}

	public void setLandmarkDay(String landmarkDay) {
		this.landmarkDay = landmarkDay;
	}

	public String getLandmarkNight() {
		return landmarkNight;
	}

	public void setLandmarkNight(String landmarkNight) {
		this.landmarkNight = landmarkNight;
	}

	public String getTimeBlock() {
		return timeBlock;
	}

	public void setTimeBlock(String timeBlock) {
		this.timeBlock = timeBlock;
	}

	public String getCalculatingMoney() {
		return calculatingMoney;
	}

	public void setCalculatingMoney(String calculatingMoney) {
		this.calculatingMoney = calculatingMoney;
	}

	public VehicleTypeModel getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleTypeModel vehicleType) {
		this.vehicleType = vehicleType;
	}

}
