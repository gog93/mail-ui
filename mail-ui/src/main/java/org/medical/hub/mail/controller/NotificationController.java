package org.medical.hub.mail.controller;

import lombok.RequiredArgsConstructor;
import org.medical.hub.mail.Notification;
import org.medical.hub.mail.repository.NotificationRepository;
import org.medical.hub.mail.service.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    @GetMapping()
    public String dashboard() {
        return "dashboard/dashboard";
    }


    @GetMapping("/notification/{id}/view")
    public String sendMail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("notification", notificationService.findById(id).get());
        return "dashboard/view";
    }
}
