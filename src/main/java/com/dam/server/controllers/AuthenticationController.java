package com.dam.server.controllers;

import com.dam.server.models.AuthenticationRequest;
import com.dam.server.models.AuthenticationResponse;
import com.dam.server.models.RegisterRequest;
import com.dam.server.services.iServices.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.beans.Transient;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    @Transient
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestParam String username, @RequestParam String newPassword){
        return authenticationService.changePassword(username, newPassword);
    }
}
