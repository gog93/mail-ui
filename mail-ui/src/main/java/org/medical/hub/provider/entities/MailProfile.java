package org.medical.hub.provider.entities;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mailProfile")
public class MailProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String profileName;
    private String host;
    private String port;
    private String username;
    private String password;
    private String transportProtocol;

    private String sslTrust;
    private String sslProtocols;

    private Boolean isSslTrust;
    private Boolean isSmtpAuth;
    private Boolean isStarttlsEnable;
    private Boolean isMailDebug;
    private Boolean isActiveProfile;
    private Boolean isDeleted;


}
