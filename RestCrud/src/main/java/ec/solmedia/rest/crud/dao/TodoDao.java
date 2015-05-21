package ec.solmedia.rest.crud.dao;

import ec.solmedia.rest.crud.model.Todo;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton which serves as the data provider for the model. We use the
 * implementation based on an enumeration.
 *
 * @author Alejandro Ayala
 */
public enum TodoDao {

    instance;
    private final Map<String, Todo> contentProvider = new HashMap<>();

    private TodoDao() {

        Todo todo = new Todo("1", "Learn REST");
        todo.setDescription("Read http://www.vogella.com/tutorials/REST/article.html");
        contentProvider.put("1", todo);
        todo = new Todo("2", "Do something");
        todo.setDescription("Read complete http://www.vogella.com");
        contentProvider.put("2", todo);

    }

    public Map<String, Todo> getModel() {
        return contentProvider;
    }
}
