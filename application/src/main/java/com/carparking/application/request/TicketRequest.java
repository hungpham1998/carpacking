package com.carparking.application.request;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TicketRequest {
	private Long id;

	@NotNull(message = "cannot be null")
	@NotEmpty(message = "cannot be empty")
	private String ticketCode;

	@NotNull(message = "cannot be null")
	@NotEmpty(message = "cannot be empty")
	private String idCard;

	private Boolean status = false;

	private String note;

	private Date dateActivation;

	private Date dateExpiration;

	@NotNull(message = "cannot be null")
	private Long ticketTypeId;

	@NotNull(message = "cannot be null")
	private Long vehicleCategoryId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTicketCode() {
		return ticketCode;
	}

	public void setTicketCode(String ticketCode) {
		this.ticketCode = ticketCode;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getDateActivation() {
		return dateActivation;
	}

	public void setDateActivation(Date dateActivation) {
		this.dateActivation = dateActivation;
	}

	public Date getDateExpiration() {
		return dateExpiration;
	}

	public void setDateExpiration(Date dateExpiration) {
		this.dateExpiration = dateExpiration;
	}

	public Long getTicketTypeId() {
		return ticketTypeId;
	}

	public void setTicketTypeId(Long ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	public Long getVehicleCategoryId() {
		return vehicleCategoryId;
	}

	public void setVehicleCategoryId(Long vehicleCategoryId) {
		this.vehicleCategoryId = vehicleCategoryId;
	}

}
