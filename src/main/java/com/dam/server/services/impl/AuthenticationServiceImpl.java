package com.dam.server.services.impl;

import com.dam.server.models.AuthenticationRequest;
import com.dam.server.models.AuthenticationResponse;
import com.dam.server.models.RegisterRequest;
import com.dam.server.models.User;
import com.dam.server.repositories.UserRepository;
import com.dam.server.services.iServices.IAuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<?> register(RegisterRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            return ResponseEntity.badRequest().body("A user already exists!");
        }
        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        User savedUser = userRepository.save(user);

        return ResponseEntity.ok("Successful register!");
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(auth);

            // Get the authenticated user
            User user = (User) auth.getPrincipal();

            return AuthenticationResponse.builder()
                    .isSuccess(true)
                    .message("Login successfully")
                    .role(user.getRole())
                    .build();
        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .isSuccess(false)
                    .message("Username or password is not correct!")
                    .build();
        }
    }

    @Override
    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        return null;
    }

    @Override
    public ResponseEntity<?> refreshToken(String refreshToken) {
        return null;
    }

    @Override
    public ResponseEntity<?> changePassword(String username, String newPassword) {
        return null;
    }
}
