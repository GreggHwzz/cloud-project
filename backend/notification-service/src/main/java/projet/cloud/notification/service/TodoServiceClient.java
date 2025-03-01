package projet.cloud.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import projet.cloud.notification.model.TodoItem;

@Service
public class TodoServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(TodoServiceClient.class);

    private final RestTemplate restTemplate;
    private final String todoServiceUrl;

    public TodoServiceClient(RestTemplate restTemplate,
            @Value("${todo.service.url}") String todoServiceUrl) {
        this.restTemplate = restTemplate;
        this.todoServiceUrl = todoServiceUrl;
    }

    public TodoItem getTodoById(Long id) {
        try {
            String url = todoServiceUrl + "/api/todos/" + id;
            logger.info("Calling Todo Service at: {}", url);

            ResponseEntity<TodoItem> response = restTemplate.getForEntity(url, TodoItem.class);
            return response.getBody();
        } catch (RestClientException e) {
            logger.error("Error calling Todo Service: {}", e.getMessage());
            return null;
        }
    }

    public TodoItem[] getAllTodos() {
        try {
            String url = todoServiceUrl + "/api/todos";
            logger.info("Calling Todo Service at: {}", url);

            ResponseEntity<TodoItem[]> response = restTemplate.getForEntity(url, TodoItem[].class);
            return response.getBody();
        } catch (RestClientException e) {
            logger.error("Error calling Todo Service: {}", e.getMessage());
            return new TodoItem[0];
        }
    }
}