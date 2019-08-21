package com.todo.rest.services.todoapp.error;


import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.todo.rest.services.todoapp.domain.TodoExceptionResponse;

@RestController
@ControllerAdvice
public class TodoExceptionHandler extends ResponseEntityExceptionHandler{
    
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleExceptions(Exception ex, WebRequest request) {
		TodoExceptionResponse todoExceptionResponse = new TodoExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<Object>(todoExceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(TodoNotFoundException.class)
	public final ResponseEntity<Object> handleTodoNotFoundException(Exception ex, WebRequest request) {
		TodoExceptionResponse todoExceptionResponse = new TodoExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<Object>(todoExceptionResponse,HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request){
		TodoExceptionResponse todoExceptionResponse = new TodoExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<Object>(todoExceptionResponse, headers, HttpStatus.BAD_REQUEST);
	}
	
}

