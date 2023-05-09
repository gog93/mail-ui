package org.medical.hub.provider.services;
import org.medical.hub.provider.dtos.NewMailProfileDto;
import org.medical.hub.provider.dtos.SendMailDto;
import org.medical.hub.provider.entities.MailProfile;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface MailService {

    String sendMail(SendMailDto sendMailDto);

    String newProfile(NewMailProfileDto request) throws Exception;

    String setActiveMailProfile(String profileName);

    String softDeleteMailProfile(String profileName);

    Object findAll();
    List<MailProfile> findAllMails();
    Optional<MailProfile> findById(Long id);

    Page<MailProfile> findAll(String search, Pageable pageable);

    Optional<MailProfile> findByProfileName(String profileName);
}
