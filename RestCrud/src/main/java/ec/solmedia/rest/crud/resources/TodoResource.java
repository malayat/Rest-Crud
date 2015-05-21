
package ec.solmedia.rest.crud.resources;

import ec.solmedia.rest.crud.dao.TodoDao;
import ec.solmedia.rest.crud.model.Todo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Alejandro Ayala
 */
public class TodoResource {
    
    @Context
    UriInfo uriInfo;
    
    @Context
    Request request;
    
    String id;

    public TodoResource(UriInfo uriInfo, Request request, String id) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
    }
    
    //Application integration
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Todo getTodo() {
        Todo todo = TodoDao.instance.getModel().get(id);
        if(todo == null) {
            throw new RuntimeException(String.format("Get APPLICATION_XML TODO with id %s not found", id));
        }
        return todo;
    }
    
    //for browser
    @GET
    @Produces(MediaType.TEXT_XML)
    public Todo getTodoHtml() {
        Todo todo = TodoDao.instance.getModel().get(id);
        if(todo == null) {
            throw new RuntimeException(String.format("Get TEXT_XML TODO with id %s not found", id));
        }
        return todo;
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public Response putTodo(JAXBElement<Todo> todo) {
        Todo t = todo.getValue();
        return putAndGetResponse(t);
    }
    
    public void deleteTodo() {
        Todo todo = TodoDao.instance.getModel().remove(id);
        if(todo == null) {
            throw new RuntimeException(String.format("Remove TODO with id %s failed", id));
        }
    }
    
    private Response putAndGetResponse(Todo todo) {
        Response res;
        
        if(TodoDao.instance.getModel().containsKey(todo.getId())) {
            res = Response.noContent().build();
        } else {
            res = Response.created(uriInfo.getAbsolutePath()).build();
        }
        TodoDao.instance.getModel().put(todo.getId(), todo);
        
        return res;
    }
}
