package org.medical.hub.provider.services.serviceImpl;


import lombok.extern.slf4j.Slf4j;
import org.medical.hub.MedicalHubApplication;
import org.medical.hub.provider.dtos.NewMailProfileDto;
import org.medical.hub.provider.dtos.SendMailDto;
import org.medical.hub.provider.entities.MailProfile;
import org.medical.hub.provider.exceptions.ProfileNotFoundException;
import org.medical.hub.provider.mailconfig.MailSetupConfig;
import org.medical.hub.provider.repositories.MailProfileRepository;
import org.medical.hub.provider.services.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.medical.hub.provider.utils.SendMailUtils.isEmailDomainValid;
import static org.medical.hub.provider.utils.SendMailUtils.replacePlaceholders;
import static org.medical.hub.provider.utils.SendMailUtils.isValidEmail;


@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    private MailProfileRepository mailProfileRepository;

    @Autowired
    private MailSetupConfig mailSetupConfig;

    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalHubApplication.class);
    private static final Marker IMPORTANT = MarkerFactory.getMarker("IMPORTANT");


    @Override
    public String sendMail(SendMailDto sendMail) {

        JavaMailSender mailSender = mailSetupConfig.getJavaMailSender();

        if (!isValidEmail(sendMail.getTo()))
            new ResponseEntity<>("Email is not valid", HttpStatus.BAD_REQUEST);
        isEmailDomainValid(sendMail.getTo());

        String text = replacePlaceholders(sendMail.getBody(), sendMail.getPlaceholders());
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setFrom(sendMail.getFrom());
            message.setTo(sendMail.getTo());
            message.setSubject(sendMail.getSubject());
            message.setText(text, true);
        };

        try {
            LOGGER.info("Beginning of log *********");
            LOGGER.info(IMPORTANT, "Sending mail to: " + sendMail.getTo());
            mailSender.send(messagePreparator);
            return "Mail sent";
        } catch (Exception e) {
            LOGGER.error(IMPORTANT, e.getMessage());
        }

        return "An Error occurred";
    }


    @Override
    public String newProfile(NewMailProfileDto request) throws Exception {

         MailProfile mailProfile = dtoToEntity(request);
        mailProfileRepository.save(mailProfile);

        return "Profile saved";
    }

    @Override
    public String setActiveMailProfile(String profileName) {
        List<MailProfile> mailProfiles = mailProfileRepository.findAll();
        MailProfile mailProfile = mailProfileRepository.findByProfileName(profileName.trim().replaceAll(" ", "_")).get();

        if (mailProfile != null) {
            mailProfiles.stream()
                    .forEach((profile) -> {
                        if (!profile.getIsDeleted()) {
                            if (profile.getProfileName().equalsIgnoreCase(profileName.trim().replaceAll(" ", "_"))) {
                                profile.setIsActiveProfile(true);

                            } else {
                                profile.setIsActiveProfile(false);
                            }
                        } else {
                            throw new ProfileNotFoundException("Profile not found");
                        }
                    });
            mailProfileRepository.saveAll(mailProfiles);
            return profileName + " successfully set as active mail profile";
        } else {
            throw new ProfileNotFoundException("Profile not found");
        }
    }


    @Override
    public String softDeleteMailProfile(String profileName) {
        MailProfile mailProfile = mailProfileRepository.findByProfileName(profileName.trim()
                .replaceAll(" ", "_")).get();

        if (mailProfile != null) {
            try {
                mailProfile.setIsDeleted(true);
            } catch (Exception ex) {
                log.info(ex.getMessage());
            }

            return profileName + " deleted successfully";
        } else {
            throw new ProfileNotFoundException("Profile not found");
        }
    }

    @Override
    public List<NewMailProfileDto> findAll() {
        List<MailProfile> all = mailProfileRepository.findAll();
        return    entityToDto(all);

    }
    public List<MailProfile> findAllMails() {
        return    mailProfileRepository.findAll();

    }
    public Optional<MailProfile> findById(Long id) {
        return    mailProfileRepository.findById( id);

    }

    @Override
    public Page<MailProfile> findAll(String search, Pageable pageable) {
        return mailProfileRepository.findAll(search,pageable);
    }

    @Override
    public Optional<MailProfile> findByProfileName(String profileName) {
        return   mailProfileRepository.findByProfileName( profileName);
    }


    protected MailProfile dtoToEntity(NewMailProfileDto request) {
        return MailProfile.builder()
                .profileName(request.getProfileName().trim().replaceAll(" ", "_"))
                .host(request.getHost())
                .port(request.getPort())
                .password(request.getPassword())
                .username(request.getUsername())
                .isSmtpAuth(request.getIsSmtpAuth())
                .isStarttlsEnable(request.getIsStarttlsEnable())
                .transportProtocol(request.getTransportProtocol())
                .isSslTrust(request.getIsSslTrust())
                .sslTrust(request.getSslTrust())
                .sslProtocols(request.getSslProtocols())
                .isMailDebug(request.getIsMailDebug())
                .isActiveProfile(false)
                .isDeleted(false)
                .build();
    }

    protected static NewMailProfileDto entityToDto(MailProfile request) {
        return NewMailProfileDto.builder()
                .profileName(request.getProfileName().trim().replaceAll(" ", "_"))
                .host(request.getHost())
                .port(request.getPort())
                .password(request.getPassword())
                .username(request.getUsername())
                .isSmtpAuth(request.getIsSmtpAuth())
                .isStarttlsEnable(request.getIsStarttlsEnable())
                .transportProtocol(request.getTransportProtocol())
                .isSslTrust(request.getIsSslTrust())
                .sslTrust(request.getSslTrust())
                .sslProtocols(request.getSslProtocols())
                .isMailDebug(request.getIsMailDebug())
                .build();
    }

    private List<NewMailProfileDto> entityToDto(List<MailProfile> mails) {
        List<NewMailProfileDto> newMailProfileDtos = mails.stream()
                .map(request -> {
                    return  NewMailProfileDto.builder()
                            .profileName(request.getProfileName().trim().replaceAll(" ", "_"))
                            .host(request.getHost())
                            .port(request.getPort())
                            .password(request.getPassword())
                            .username(request.getUsername())
                            .isSmtpAuth(request.getIsSmtpAuth())
                            .isStarttlsEnable(request.getIsStarttlsEnable())
                            .transportProtocol(request.getTransportProtocol())
                            .isSslTrust(request.getIsSslTrust())
                            .sslTrust(request.getSslTrust())
                            .sslProtocols(request.getSslProtocols())
                            .isMailDebug(request.getIsMailDebug())
                            .build();
                })
                .collect(Collectors.toList());
        return newMailProfileDtos;
    }
}
