package org.medical.hub.provider.mailconfig;


import org.medical.hub.provider.entities.MailProfile;
import org.medical.hub.provider.repositories.MailProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


import java.util.Properties;

@Configuration
public class MailSetupConfig {

    @Autowired
    private MailProfileRepository mailProfileRepository;


    public JavaMailSender getJavaMailSender() {

        MailProfile profile = mailProfileRepository.findByIsActiveProfile(true);

        if (profile != null) {
            String host = profile.getHost();
            int port = Integer.parseInt(profile.getPort());
            String username = profile.getUsername();
            String password = profile.getPassword();
            String transportProtocol = profile.getTransportProtocol();
            Boolean isSmtpAuth = profile.getIsSmtpAuth();
            Boolean isStarttlsEnable = profile.getIsStarttlsEnable();
            Boolean isMailDebug = profile.getIsMailDebug();

            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(host);
            mailSender.setPort(port);
            mailSender.setUsername(username);
            mailSender.setPassword(password);

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", transportProtocol);
            props.put("mail.smtp.auth", String.valueOf(isSmtpAuth));
            props.put("mail.smtp.starttls.enable", String.valueOf(isStarttlsEnable));
            props.put("mail.debug", String.valueOf(isMailDebug));

            System.out.println("mail sender: " + mailSender);
            return mailSender;
        }
        return new JavaMailSenderImpl();
    }

}
