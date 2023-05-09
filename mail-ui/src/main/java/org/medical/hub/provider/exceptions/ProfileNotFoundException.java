package org.medical.hub.provider.exceptions;


import lombok.Data;

@Data
public class ProfileNotFoundException extends RuntimeException{

    public ProfileNotFoundException(String message) {
        super(message);
    }
}
