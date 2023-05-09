package org.medical.hub.provider.controllers;

import org.medical.hub.provider.entities.MailProfile;
import org.medical.hub.provider.services.MailService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Pageable;
@RestController
public class PaginationController {
    private final MailService service;

    public PaginationController(MailService service) {
        this.service = service;
    }

    @GetMapping("/page")
    public Page<MailProfile> allDraft(@RequestParam(name = "search", required = false) String search,
                                      Pageable pageable) {
        Page<MailProfile> byStatus = service.findAll(search, pageable);

        return byStatus;
    }
    @DeleteMapping("/profile/delete")
    public ResponseEntity<Void> deleteMailProfile(@RequestParam(name = "profileName") String profileName) {
        service.softDeleteMailProfile(profileName);
        return ResponseEntity.noContent().build();
    }
}
