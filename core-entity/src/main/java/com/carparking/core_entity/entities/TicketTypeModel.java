package com.carparking.core_entity.entities;

import javax.persistence.*;

@Entity
@Table(name = "ticket_type")
public class TicketTypeModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "ticket_type_code")
	private String ticketTypeCode;

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

	public String getTicketTypeCode() {
		return ticketTypeCode;
	}

	public void setTicketTypeCode(String ticketTypeCode) {
		this.ticketTypeCode = ticketTypeCode;
	}

}
