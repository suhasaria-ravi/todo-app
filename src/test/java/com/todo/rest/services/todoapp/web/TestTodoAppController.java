package com.todo.rest.services.todoapp.web;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.todo.rest.services.todoapp.domain.ItemStatus;
import com.todo.rest.services.todoapp.domain.Todo;
import com.todo.rest.services.todoapp.repo.TodoAppRepo;
import com.todo.rest.services.todoapp.service.TodoService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TodoAppController.class, secure = false)
public class TestTodoAppController {

	private static final int CREATED_TODO_ID = 4;
	private static final int UPDATED_TODO_ID = 4;
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	private TodoService todoService;		

	@Test
	public void retrieveTodos() throws Exception { 
		List<Todo> mockList = Arrays.asList(new Todo(1, "NameMock1", "DescriptionMock1", "DueDateMock1", ItemStatus.PENDING),
											new Todo(2, "NameMock2", "DescriptionMock2", "DueDateMock2", ItemStatus.DONE));

		when(todoService.getAllTodo()).thenReturn(mockList);

		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/app/api/todos").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		
		String expected = "[" +  " {\r\n" + 
				"	        \"id\": 1,\r\n" + 
				"	        \"name\": \"NameMock1\",\r\n" + 
				"	        \"description\": \"DescriptionMock1\",\r\n" + 
				"	        \"dueDate\": \"DueDateMock1\",\r\n" + 
				"	        \"status\": \"PENDING\"\r\n" + 
				"	    },\r\n" + 
				"	    {\r\n" + 
				"	        \"id\": 2,\r\n" + 
				"	        \"name\": \"NameMock2\",\r\n" + 
				"	        \"description\": \"DescriptionMock2\",\r\n" + 
				"	        \"dueDate\": \"DueDateMock2\",\r\n" + 
				"	        \"status\": \"DONE\"\r\n" + 
				"	    }"   + "]";
		
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	public void retrieveTodo() throws Exception {
		Todo mockTodo = new Todo(1, "NameMock1", "DescriptionMock1", "DueDateMock1", ItemStatus.PENDING);
		when(todoService.getTodoItem(anyInt())).thenReturn(mockTodo);
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.get("/app/api/todo/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String expected = "{\r\n" + 
				"	        \"id\": 1,\r\n" + 
				"	        \"name\": \"NameMock1\",\r\n" + 
				"	        \"description\": \"DescriptionMock1\",\r\n" + 
				"	        \"dueDate\": \"DueDateMock1\",\r\n" + 
				"	        \"status\": \"PENDING\"\r\n" + 
				"	    }";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void retrieveTodo_error() throws Exception {
		when(todoService.getTodoItem(anyInt())).thenReturn(null);
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.get("/app/api/todo/11").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn(); 
	}

	@Test
	public void createTodo() throws Exception {
		Todo mockTodo = new Todo(CREATED_TODO_ID, "NameMock4", "DescMock4", "DateMock4", ItemStatus.PENDING);
		String todo =  "{\r\n" + 
				"	        \"id\": 4,\r\n" + 
				"	        \"name\": \"NameMock4\",\r\n" + 
				"	        \"description\": \"DescMock4\",\r\n" + 
				"	        \"dueDate\": \"DateMock4\",\r\n" + 
				"	        \"status\": \"PENDING\"\r\n" + 
				"	    }";

		when(todoService.saveTodo(any())).thenReturn(mockTodo);
		mvc.perform(
				MockMvcRequestBuilders.post("/app/api/add").content(todo).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	public void createTodo_error() throws Exception {

		String todo =  "{\r\n" + 
				"	        \"id\": 4,\r\n" + 
				"	        \"name\": \"\",\r\n" + 
				"	        \"description\": \"DescMock4\",\r\n" + 
				"	        \"dueDate\": \"DateMock4\",\r\n" + 
				"	        \"status\": \"DONE\"\r\n" + 
				"	    }";

		MvcResult result = mvc.perform(
				MockMvcRequestBuilders.post("/app/api/add").content(todo).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError()).andReturn();
	}

	/*@Test
	public void updateTodo() throws Exception {
		Todo mockTodo = new Todo(UPDATED_TODO_ID, "Jack", "Learn Spring MVC 5", new Date(), false);
		String todo = "{\"user\":\"Jack\",\"desc\":\"Learn Spring MVC 5\",\"done\":false}";

		when(service.update(mockTodo)).thenReturn(mockTodo);

		MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/users/Jack/todos/" + UPDATED_TODO_ID).content(todo)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

		JSONAssert.assertEquals(todo, result.getResponse().getContentAsString(), false);
	}*/

	@Test
	public void deleteTodo() throws Exception {
		Todo mockTodo = new Todo(1,"Name1", "Description1", "DueDate1", ItemStatus.PENDING);
		when(todoService.deleteItem(anyInt())).thenReturn(true);		
		mvc.perform(MockMvcRequestBuilders.delete("/app/api/delete/" + mockTodo.getId())
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();			
	}

	@Test
	public void deleteTodo_error() throws Exception {
		Todo mockTodo = new Todo(55,"Name1", "Description1", "DueDate1", ItemStatus.PENDING);
		when(todoService.deleteItem(anyInt())).thenReturn(false);
		mvc.perform(MockMvcRequestBuilders.delete("/app/api/delete/"+ mockTodo.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn();		
	}


}
