package org.medical.hub.mail.repository;

import org.medical.hub.mail.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
