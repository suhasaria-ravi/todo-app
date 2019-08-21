package com.todo.rest.services.todoapp.web;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.todo.rest.services.todoapp.domain.Todo;
import com.todo.rest.services.todoapp.service.TodoService;

@RestController
@RequestMapping("/app/api")
@CrossOrigin
public class TodoAppController {

	@Autowired
	private TodoService todoService;

	@GetMapping(value = "/todos")
	public  ResponseEntity<List<Todo>> getTodoList() throws Exception {
		return new ResponseEntity<>(todoService.getAllTodo(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/todo/{id}")
	public  ResponseEntity<Todo> getTodo(@PathVariable int id) {	
		return new ResponseEntity<>(todoService.getTodoItem(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/add")
	public  ResponseEntity<Todo> addTodo(@RequestBody @Valid Todo todo) {
		return new ResponseEntity<>(todoService.saveTodo(todo), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public  ResponseEntity<Todo> deleteTodo(@PathVariable int id) {	
		todoService.deleteItem(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
