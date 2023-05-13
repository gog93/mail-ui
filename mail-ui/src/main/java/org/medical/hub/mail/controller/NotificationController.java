package org.medical.hub.mail.controller;

import lombok.RequiredArgsConstructor;
import org.medical.hub.mail.service.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping()
    public String dashboard(Model model) {
        return "dashboard/dashboard";
    }

    @GetMapping("/notification/{id}/view")
    public String sendMail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("notification", notificationService.findById(id).get());
        return "dashboard/view";
    }
}
