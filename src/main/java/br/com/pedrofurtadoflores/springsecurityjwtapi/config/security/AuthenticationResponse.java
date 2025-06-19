package br.com.pedrofurtadoflores.springsecurityjwtapi.config.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
}
