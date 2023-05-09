package org.medical.hub.provider.controllers;



import lombok.RequiredArgsConstructor;
import org.medical.hub.provider.dtos.NewMailProfileDto;
import org.medical.hub.provider.services.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService service;


    @PostMapping("/send")
    public String sendMail(Model model){
        model.addAttribute("provider", new NewMailProfileDto());
        return "create";
    }

    @GetMapping("/profile/providers")
    public String provider(Model model) {

        model.addAttribute("providers", service.findAll());
        model.addAttribute("provider", new NewMailProfileDto());
        return "provider/index";
    }
    @GetMapping("/profile")
    public String createMailSetup(Model model) {
        model.addAttribute("provider", new NewMailProfileDto());
        return "provider/create";
    }
    @GetMapping("/view")
    public String view(Model model) {
        return "example";
    }


    @PostMapping("/profile")
    public String newMailProfile(Model model, NewMailProfileDto newMailProfileDto) throws Exception {
        service.newProfile(newMailProfileDto);
        model.addAttribute("provider", new NewMailProfileDto());
        return "redirect:/api/v1/mail/profile/providers";    }


    @PatchMapping("/profile")
    public ResponseEntity<String> setActiveMailProfile(@RequestParam(name = "ProfileName") String profileName) {
        return ResponseEntity.ok(service.setActiveMailProfile(profileName));
    }




}
