package com.todo.rest.services.todoapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.todo.rest.services.todoapp.domain.Todo;
import com.todo.rest.services.todoapp.error.TodoNotFoundException;
import com.todo.rest.services.todoapp.repo.TodoAppRepo;

@Service
public class TodoService {	
	
	private TodoAppRepo todoAppRepo; 
	
	@Autowired
	public TodoService(TodoAppRepo todoAppRepo) {
		this.todoAppRepo = todoAppRepo;
	}

    public List<Todo> getAllTodo() throws Exception {
		return todoAppRepo.getAllTodo();		
    }
    
    public  Todo saveTodo(Todo todoItem) {
        return todoAppRepo.saveTodo(todoItem);
    }
    
    public  Todo getTodoItem(int id) {
		Todo todoItem = todoAppRepo.getTodoItem(id);	
    	if(todoItem==null) throw new TodoNotFoundException("Todo item with id="+id+" not found");
        return todoItem;
    }
    
    public Boolean deleteItem(int id) {
		Boolean itemDeletedInd = todoAppRepo.deleteTodoItem(id);	
    	if(!itemDeletedInd) throw new TodoNotFoundException("Todo item with id="+id+" not found"); 
    	return itemDeletedInd;
    }
		
	
}
