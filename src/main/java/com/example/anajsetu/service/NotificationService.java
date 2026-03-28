package com.example.anajsetu.service;

import com.example.anajsetu.model.Notification;
import com.example.anajsetu.model.User;
import com.example.anajsetu.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public Notification createNotification(User user, String message, String type) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setType(type);
        notification.setIsRead(0);
        notification.setCreatedAt(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getNotificationsByUser(int userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<Notification> getUnreadNotifications(int userId) {
        return notificationRepository.findByUserIdAndIsRead(userId, 0);
    }

    public Notification markAsRead(int id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        if (notification.isPresent()) {
            notification.get().setIsRead(1);
            return notificationRepository.save(notification.get());
        }
        return null;
    }

    public void markAllAsRead(int userId) {
        List<Notification> notifications = notificationRepository.findByUserIdAndIsRead(userId, 0);
        for (Notification notification : notifications) {
            notification.setIsRead(1);
            notificationRepository.save(notification);
        }
    }

    public void deleteNotification(int id) {
        notificationRepository.deleteById(id);
    }

}