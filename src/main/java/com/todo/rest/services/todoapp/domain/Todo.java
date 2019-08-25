package com.todo.rest.services.todoapp.domain;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Todo {
		 
	private int id;
	
	@NotNull @NotBlank
	private String name;
	
	@Size(max=500, message = "Description can be max 500 Chars")
	private String description;
	

    @JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dueDate;
	
	private ItemStatus status;

	public Todo(int id,String name, String description, LocalDate dueDate, ItemStatus status) {
		this.id=id;
		this.name = name;
		this.description = description;
		this.dueDate = dueDate;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public ItemStatus getStatus() {
		return status;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public void setStatus(ItemStatus status) {
		this.status = status;
	}
}
