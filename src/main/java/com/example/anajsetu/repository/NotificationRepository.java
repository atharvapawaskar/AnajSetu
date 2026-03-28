package com.example.anajsetu.repository;

import com.example.anajsetu.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findByUserId(int userId);

    List<Notification> findByUserIdAndIsRead(int userId, int isRead);

    List<Notification> findByType(String type);

    List<Notification> findByUserIdOrderByCreatedAtDesc(int userId);

}