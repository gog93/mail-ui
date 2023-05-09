package org.medical.hub.mail.service;

import org.medical.hub.mail.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getNotificationsByProviderName(String name);
}
