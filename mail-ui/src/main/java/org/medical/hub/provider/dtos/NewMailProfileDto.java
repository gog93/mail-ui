package org.medical.hub.provider.dtos;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewMailProfileDto {

    private String profileName;
    private String host;
    private String port;
    private String username;
    private String password;
    private String transportProtocol;
    private Boolean isSmtpAuth;
    private Boolean isStarttlsEnable;
    private Boolean isMailDebug;
    private String sslTrust;
    private String sslProtocols;
    private Boolean isSslTrust;
}
