package com.todo.rest.services.todoapp.repo;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.todo.rest.services.todoapp.domain.ItemStatus;
import com.todo.rest.services.todoapp.domain.Todo;

@Component
public class TodoAppRepo {

	private static HashMap<Integer, Todo> todo = new HashMap<Integer, Todo>();

	static {
		todo.put(1,new Todo(1,"Name1", "Description1", LocalDate.now(), ItemStatus.PENDING));
		todo.put(2,new Todo(2,"Name2", "Description2", LocalDate.now(), ItemStatus.PENDING));
		todo.put(3,new Todo(3,"Name3", "Description3", LocalDate.now(), ItemStatus.DONE));
	}

	public List<Todo> getAllTodo() {
		List<Todo> todos = todo.values().stream().collect(Collectors.toList());
		return todos;
	}

	public Todo saveTodo(Todo todoItem) {
		//overriding the Id, if it was sent from front end
		//always using generated id, instead of id passed from request
		todoItem.setId(getNextTodoId());
		todoItem.setStatus(ItemStatus.fromValue(todoItem.getStatus().toString()));
		todo.put(todoItem.getId(),todoItem);
		return todoItem;
	}

	public Todo getTodoItem(int id) {
		
		for(HashMap.Entry<Integer, Todo> entry : todo.entrySet()) {
		    if(entry.getKey()==id) {
		    	return entry.getValue();
		    }
		}
		return null;
	}
	
	public Todo updateTodoItem(int id,Todo todoItem) {
		
		for(HashMap.Entry<Integer, Todo> entry : todo.entrySet()) {
		    if(entry.getKey()==id) {
		    	//overriding the item's id - 
		    	//in case the item contains id value different from URL id value
		    	//Or in case the id value is null
		    	todoItem.setId(entry.getKey());
		    	todo.replace(entry.getKey(), todoItem);
		    	return todo.get(entry.getKey());
		    }
		}
		return null;
	}
	
	public Boolean deleteTodoItem(int id) {
		
		for(HashMap.Entry<Integer, Todo> entry : todo.entrySet()) {
		    if(entry.getKey()==id) {
		    	todo.remove(entry.getKey());
		    	return true;
		    }
		}
		return false;
	}
	
	public int getNextTodoId() {
		int maxValue=0;
		for(HashMap.Entry<Integer, Todo> entry : todo.entrySet()) {
			if(entry.getKey()>maxValue) {
				maxValue=entry.getKey();
			}
		}
		return maxValue+1;
	}

}
