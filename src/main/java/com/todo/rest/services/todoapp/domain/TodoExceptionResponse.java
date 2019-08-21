package com.todo.rest.services.todoapp.domain;

import java.util.Date;

public class TodoExceptionResponse {

	private Date date; 
	private String message;
	private String details;
	
	public TodoExceptionResponse(Date date, String message, String details) {
		super();
		this.date = date;
		this.message = message;
		this.details = details;
	}

	public Date getDate() {
		return date;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
