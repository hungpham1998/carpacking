package com.carparking.application.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class VehicleCategoryRequest {

	private Long id;

	@NotNull(message = "cannot be null")
	@NotEmpty(message = "cannot be empty")
	private String nameType;

	@NotNull(message = "cannot be null")
	private Float unitPrice;

	@NotNull(message = "cannot be null")
	private Float unitPriceBlock;

	private Float unitPriceNight;

	private Float unitPriceTurn;

	private String landmarkDay;

	private String landmarkNight;

	private String timeBlock;

	private String calculatingMoney;

	@NotNull(message = "cannot be null")
	private Long vehicleTypeId;

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

	public Long getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(Long vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

}
