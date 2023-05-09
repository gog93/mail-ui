package org.medical.hub.mail.controller;

import org.medical.hub.mail.Notification;
import org.medical.hub.mail.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private  NotificationService service ;


    @GetMapping("/{name}")
   @ResponseBody
    public  Map<Date, Integer> getNotifications(@PathVariable("name") String name) {
        Map<Date, Integer> date=new HashMap<>();
        int i=1;
        List<Notification>notifications = service.getNotificationsByProviderName(name);
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
