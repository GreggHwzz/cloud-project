package projet.cloud.notification.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet.cloud.notification.model.Notification;
import projet.cloud.notification.service.NotificationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Optional<Notification> notification = notificationService.getNotificationById(id);
        return notification.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/todo/{todoId}")
    public List<Notification> getNotificationsForTodo(@PathVariable Long todoId) {
        return notificationService.getNotificationsForTodo(todoId);
    }

    @GetMapping("/pending")
    public List<Notification> getPendingNotifications() {
        return notificationService.getPendingNotifications();
    }

    @PostMapping("/todo/{todoId}")
    public ResponseEntity<?> createNotification(
            @PathVariable Long todoId,
            @RequestBody Map<String, String> payload) {

        try {
            String message = payload.get("message");
            if (message == null || message.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Message cannot be empty"));
            }

            Notification notification = notificationService.createNotification(todoId, message);
            return ResponseEntity.status(HttpStatus.CREATED).body(notification);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating notification: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Unexpected error creating notification", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred"));
        }
    }

    @PostMapping("/todo/{todoId}/completion")
    public ResponseEntity<?> createCompletionNotification(@PathVariable Long todoId) {
        try {
            Notification notification = notificationService.createTodoCompletionNotification(todoId);
            return ResponseEntity.status(HttpStatus.CREATED).body(notification);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error creating completion notification", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred"));
        }
    }

    @PutMapping("/{id}/sent")
    public ResponseEntity<?> markAsSent(@PathVariable Long id) {
        boolean updated = notificationService.markAsSent(id);

        if (updated) {
            return ResponseEntity.ok().body(Map.of("status", "Notification marked as sent"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long id) {
        try {
            notificationService.deleteNotification(id);
            return ResponseEntity.ok().body(Map.of("status", "Notification deleted"));
        } catch (Exception e) {
            logger.error("Error deleting notification", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error deleting notification"));
        }
    }
}