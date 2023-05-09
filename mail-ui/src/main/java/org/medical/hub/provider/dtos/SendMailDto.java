package org.medical.hub.provider.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendMailDto {

    private String from;
    private String to;
    private String subject;
    private String body;
    private String placeholders;

}
