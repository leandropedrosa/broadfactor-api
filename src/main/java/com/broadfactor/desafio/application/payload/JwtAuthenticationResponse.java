package com.broadfactor.desafio.application.payload;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private UserProfile userProfile;

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
