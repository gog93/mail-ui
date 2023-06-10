package org.medical.hub.mail.controller;

import org.medical.hub.mail.Notification;
import org.medical.hub.mail.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
@CrossOrigin(origins = "http://localhost")

public class NotificationRestController {
    @Autowired
    private NotificationService service;


    @GetMapping()
    public List<Notification> allNotifications() {

        return service.findAllNewNotifications();
    }

    @PostMapping("/view/{id}")
    public Notification viewNot(@PathVariable("id") Long id) {

        return service.changeStatus(id);
    }

    @GetMapping("/{name}")
    @ResponseBody
    public Map<Date, Integer> getNotifications(@PathVariable("name") String name) {
        Map<Date, Integer> dateMap = new HashMap<>();
        int i = 1;
        List<Notification> notifications = service.getNotificationsByProviderName(name);
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Notification note : notifications) {
            try {
                String dateString = note.getDate().toString().substring(0, 10);
                Date noteDate = inputDateFormat.parse(dateString);

                if (dateMap.containsKey(noteDate)) {
                    int count = dateMap.get(noteDate);
                    dateMap.put(noteDate, count + 1);
                } else {
                    dateMap.put(noteDate, i);
                }
            } catch (ParseException e) {
                // Handle parse exception if necessary
            }
        }

        return dateMap;
    }
}
