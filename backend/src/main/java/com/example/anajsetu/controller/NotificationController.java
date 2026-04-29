package com.example.anajsetu.controller;

import com.example.anajsetu.model.Notification;
import com.example.anajsetu.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // CREATE NOTIFICATION
    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        Notification saved = notificationService.createNotification(
            notification.getUser(),
            notification.getMessage(),
            notification.getType()
        );
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // GET ALL NOTIFICATIONS
    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    // GET NOTIFICATIONS BY USER
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUser(@PathVariable int userId) {
        List<Notification> notifications = notificationService.getNotificationsByUser(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    // GET UNREAD NOTIFICATIONS BY USER
    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable int userId) {
        List<Notification> notifications = notificationService.getUnreadNotifications(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    // MARK NOTIFICATION AS READ
    @PutMapping("/{id}/read")
    public ResponseEntity<?> markAsRead(@PathVariable int id) {
        Notification notification = notificationService.markAsRead(id);
        if (notification != null) {
            return new ResponseEntity<>(notification, HttpStatus.OK);
        }
        return new ResponseEntity<>("Notification not found", HttpStatus.NOT_FOUND);
    }

    // MARK ALL NOTIFICATIONS AS READ
    @PutMapping("/user/{userId}/readall")
    public ResponseEntity<String> markAllAsRead(@PathVariable int userId) {
        notificationService.markAllAsRead(userId);
        return new ResponseEntity<>("All notifications marked as read", HttpStatus.OK);
    }

    // DELETE NOTIFICATION
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable int id) {
        notificationService.deleteNotification(id);
        return new ResponseEntity<>("Notification deleted successfully", HttpStatus.OK);
    }

}