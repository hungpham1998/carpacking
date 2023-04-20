package com.carparking.application.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TicketTypeRequest {
	private Long id;

	@NotNull(message = "cannot be null")
	@NotEmpty(message = "cannot be empty")
	private String title;

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
