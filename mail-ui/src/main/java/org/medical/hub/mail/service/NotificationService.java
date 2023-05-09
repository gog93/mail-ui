package org.medical.hub.mail.service;

import org.medical.hub.mail.Notification;
import org.medical.hub.mail.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getNotifications() {
        List<Notification> notifications = notificationRepository.findAll();

        return notifications;
    }
}
