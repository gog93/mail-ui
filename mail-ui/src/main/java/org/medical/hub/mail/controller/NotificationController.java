package org.medical.hub.mail.controller;

import org.medical.hub.mail.Notification;
import org.medical.hub.mail.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public Map<Date, Integer> getNotifications() {
        Map<Date, Integer> date=new HashMap<>();
        int i=1;
        List<Notification>notifications = notificationService.getNotifications();
        for (Notification note: notifications) {
            if (date.containsKey(note.getDate())){
                int a=date.get(note.getDate());
                date.put(note.getDate(),++a);
            }else {
                date.put(note.getDate(),i);
            }
        }
        return date;
    }
}
