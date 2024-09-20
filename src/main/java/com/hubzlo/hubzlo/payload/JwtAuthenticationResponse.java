package com.hubzlo.hubzlo.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer"; // Tipo de token padrão é Bearer

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
