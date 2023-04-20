package com.carparking.core_entity.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ticket")
public class TicketModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ticketCode")
	private String ticketCode;

	@Column(name = "idCard")
	private String idCard;

	@Column(name = "status")
	private Boolean status;

	@Column(name = "note", columnDefinition = "TEXT")
	private String note;

	@Column(name = "dateActivation")
	private Date dateActivation;

	@Column(name = "dateExpiration")
	private Date dateExpiration;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ticket_type_id", referencedColumnName = "id", nullable = false)
//	@OnDelete(action = OnDeleteAction.CASCADE)
	private TicketTypeModel ticketType;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "vehicle_category_id", referencedColumnName = "id", nullable = false)
//	@OnDelete(action = OnDeleteAction.CASCADE)
	private VehicleCategoryModel vehicleCategory;

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

	public TicketTypeModel getTicketType() {
		return ticketType;
	}

	public void setTicketType(TicketTypeModel ticketType) {
		this.ticketType = ticketType;
	}

	public VehicleCategoryModel getVehicleCategory() {
		return vehicleCategory;
	}

	public void setVehicleCategory(VehicleCategoryModel vehicleCategory) {
		this.vehicleCategory = vehicleCategory;
	}

}
