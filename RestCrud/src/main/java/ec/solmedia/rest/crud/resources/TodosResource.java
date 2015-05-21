/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.solmedia.rest.crud.resources;

import ec.solmedia.rest.crud.dao.TodoDao;
import ec.solmedia.rest.crud.model.Todo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Alejandro Ayala
 */
// Will map the resource to the URL todos
@Path("todos")
public class TodosResource {

    @Context
    UriInfo uriInfo;

    @Context
    Request request;

    // Return the list of todos to the user in the browser
    @GET
    @Produces(MediaType.TEXT_XML)
    public List<Todo> getTodosBrowser() {
        List<Todo> listTodo = new ArrayList<>();
        listTodo.addAll(TodoDao.instance.getModel().values());
        return listTodo;
    }

    // Return the list of todos for applications
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Todo> getTodos() {
        List<Todo> listTodo = new ArrayList<>();
        listTodo.addAll(TodoDao.instance.getModel().values());
        return listTodo;
    }

    // retuns the number of todos
    // Use http://localhost:8080/RestCrud-1.0-SNAPSHOT/webresources/todos/count
    // to get the total number of records
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        int size = TodoDao.instance.getModel().size();
        return Integer.toString(size);
    }
    
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void newTodo(@FormParam("id") String id,
            @FormParam("summary") String summary, 
            @FormParam("description") String description,
            @Context HttpServletResponse servletResponse) throws IOException {
        Todo todo = new Todo(id, summary);
        
        if(description != null) {
            todo.setDescription(description);
        }
        TodoDao.instance.getModel().put(id, todo);
        
        servletResponse.sendRedirect("../create_todo.html");
    }
    
    @Path("{id}")
    public TodoResource getTodo(@PathParam("id") String id) {
        return new TodoResource(uriInfo, request, id);
    }
}
