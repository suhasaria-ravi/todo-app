package com.todo.rest.services.todoapp.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.todo.rest.services.todoapp.util.EnumHandler;

public enum ItemStatus {
	PENDING,
	DONE;
	    
    @JsonValue
    public String getValue() {
    	return name();
    }
    
    @JsonCreator
    public static ItemStatus fromValue(String inputString) {
    	return EnumHandler.getEnumFromString(ItemStatus.class, inputString);
    }	
	    
}
