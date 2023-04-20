package com.carparking.application.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtAuthenticationResponse {
    private String accessToken;
    
    // private String tokenType = "Bearer";

    public JwtAuthenticationResponse(String accessToken) {

        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
