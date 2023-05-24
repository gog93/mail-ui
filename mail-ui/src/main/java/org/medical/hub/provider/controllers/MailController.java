package org.medical.hub.provider.controllers;



import lombok.RequiredArgsConstructor;
import org.medical.hub.provider.dtos.NewMailProfileDto;
import org.medical.hub.provider.entities.MailProfile;
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


    @GetMapping("/profile/providers")
    public String provider(Model model) {

        model.addAttribute("providers", service.findByIsDeleted());
        model.addAttribute("provider", new NewMailProfileDto());
        return "provider/index";
    }
    @GetMapping("/profile")
    public String createMailSetup(Model model) {
        model.addAttribute("provider", new NewMailProfileDto());
        return "provider/create";
    }
    @GetMapping("/view/{profileName}")
    public String view(Model model,@PathVariable("profileName") String profileName) {
       NewMailProfileDto mailProfile= service.findByProfileName(profileName);
        model.addAttribute("provider",mailProfile)  ;

        return "provider/view";
    }


    @PostMapping("/profile")
    public String newMailProfile(Model model, NewMailProfileDto newMailProfileDto) throws Exception {
        service.newProfile(newMailProfileDto);
        model.addAttribute("provider", new NewMailProfileDto());
        return "redirect:/api/v1/mail/profile/providers";    }

    @PostMapping("/profile/edit/{id}")
    public String newMailProfileEdit(@PathVariable("id")Long id, Model model, MailProfile newMailProfileDto) throws Exception {
        service.editProfile(id,newMailProfileDto);
        model.addAttribute("provider", new NewMailProfileDto());
        return "redirect:/api/v1/mail/profile/providers";    }

    @GetMapping("/profile/edit/{id}")
    public String editMailProfile(@PathVariable("id")Long id, Model model) throws Exception {
        model.addAttribute("provider", service.findById(id).get());
        return "provider/edit";    }


    @PatchMapping("/profile")
    public ResponseEntity<String> setActiveMailProfile(String profileName) {
        return ResponseEntity.ok(service.setActiveMailProfile(profileName));
    }




}
