package com.dam.server.services.iServices;

import com.dam.server.models.RegisterRequest;
import com.dam.server.models.AuthenticationResponse;
import com.dam.server.models.AuthenticationRequest;
import org.springframework.http.ResponseEntity;

public interface IAuthenticationService {
    ResponseEntity<?> register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    ResponseEntity<?> confirmEmail(String confirmationToken);
    ResponseEntity<?> refreshToken(String refreshToken);
    ResponseEntity<?> changePassword(String username,String newPassword);
}
