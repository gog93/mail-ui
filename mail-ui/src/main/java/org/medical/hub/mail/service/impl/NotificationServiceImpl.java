package org.medical.hub.mail.service.impl;

import org.medical.hub.mail.Notification;
import org.medical.hub.mail.repository.NotificationRepository;
import org.medical.hub.mail.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getNotificationsByProviderName(String name) {
        List<Notification> notifications = notificationRepository.findByProviderName(name);

        return notifications;
    }
}
