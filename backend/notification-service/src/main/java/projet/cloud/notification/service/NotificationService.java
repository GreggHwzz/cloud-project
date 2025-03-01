package projet.cloud.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import projet.cloud.notification.model.Notification;
import projet.cloud.notification.model.TodoItem;
import projet.cloud.notification.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;
    private final TodoServiceClient todoServiceClient;

    public NotificationService(NotificationRepository notificationRepository,
            TodoServiceClient todoServiceClient) {
        this.notificationRepository = notificationRepository;
        this.todoServiceClient = todoServiceClient;
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public List<Notification> getNotificationsForTodo(Long todoId) {
        return notificationRepository.findByTodoId(todoId);
    }

    public Notification createNotification(Long todoId, String message) {
        // First check if the todo exists
        TodoItem todoItem = todoServiceClient.getTodoById(todoId);

        if (todoItem == null) {
            logger.warn("Attempted to create notification for non-existent todo id: {}", todoId);
            throw new IllegalArgumentException("Todo item with ID " + todoId + " not found");
        }

        Notification notification = new Notification(todoId, message);
        notification.setCreatedAt(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    public Notification createTodoCompletionNotification(Long todoId) {
        TodoItem todoItem = todoServiceClient.getTodoById(todoId);

        if (todoItem == null) {
            logger.warn("Attempted to create completion notification for non-existent todo id: {}", todoId);
            throw new IllegalArgumentException("Todo item with ID " + todoId + " not found");
        }

        String message = "Task completed: " + todoItem.getTitle();
        Notification notification = new Notification(todoId, message);
        return notificationRepository.save(notification);
    }

    public boolean markAsSent(Long id) {
        Optional<Notification> optionalNotification = notificationRepository.findById(id);

        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            notification.setSent(true);
            notificationRepository.save(notification);
            return true;
        }

        return false;
    }

    public List<Notification> getPendingNotifications() {
        return notificationRepository.findBySent(false);
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}