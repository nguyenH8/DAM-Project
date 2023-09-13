package com.dam.server.models;

import com.dam.server.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@AllArgsConstructor
@Data
public class RegisterRequest {
    private final String username;
    private final String password;
    private final String email;

    @Enumerated(EnumType.STRING)
    private final Role role;
}
