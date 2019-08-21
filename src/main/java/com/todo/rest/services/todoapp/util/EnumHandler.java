package com.todo.rest.services.todoapp.util;

import com.todo.rest.services.todoapp.error.TodoNotFoundException;

public class EnumHandler {
	
	 //The following function provides a means to create a enum from case insensitive strings.    
    public static  <T extends Enum<T>> T getEnumFromString(Class<T> enumClass, String value) {
        StringBuilder errorMessageValue = null;
        if (enumClass != null) {
            for (Enum<?> enumValue : enumClass.getEnumConstants()) {
                if (enumValue.toString().equalsIgnoreCase(value)) {
                    return (T) enumValue;
                }
            }
            errorMessageValue = new StringBuilder();
            boolean bFirstTime = true;
            for (Enum<?> enumValue : enumClass.getEnumConstants()) {
                errorMessageValue.append(bFirstTime ? "" : ", ").append(enumValue);
                bFirstTime = false;
            }
            throw new TodoNotFoundException("Status" + value + " is invalid value. Supported values are " + errorMessageValue);
        }

        throw new TodoNotFoundException("Status value can't be null.");
    }

}
