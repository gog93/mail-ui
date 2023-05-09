package org.medical.hub.mail.repository;

import org.medical.hub.mail.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
  List<Notification> findByProviderName(String name);
}
