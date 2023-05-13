package org.medical.hub.mail.service.impl;

import org.medical.hub.mail.Notification;
import org.medical.hub.mail.repository.NotificationRepository;
import org.medical.hub.mail.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification changeStatus(Long id) {
       Notification byId = notificationRepository.findById(id).get();
       byId.setStatus("viewed");
        return  notificationRepository.save(byId);

    }

    @Override
    public List<Notification> findAllNewNotifications() {

        return notificationRepository.findByStatus("unread");
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return  notificationRepository.findById(id);
    }
}
