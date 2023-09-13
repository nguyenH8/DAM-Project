package com.dam.server.models;

import com.dam.server.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    private Boolean isSuccess;
    private String message;
    private Role role;
}
