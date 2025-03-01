package projet.cloud.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projet.cloud.notification.model.Notification;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByTodoId(Long todoId);

    List<Notification> findBySent(boolean sent);
}