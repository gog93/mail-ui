package org.medical.hub.mail.service;

import org.medical.hub.mail.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {
    List<Notification> getNotificationsByProviderName(String name);

    List<Notification> findAll();
    Notification changeStatus(Long id);

    List<Notification> findAllNewNotifications();

    Optional<Notification> findById(Long id);
}
