package com.dam.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {

    @Size(min=3, max=20, message="Username must be between 3 and 20 characters!")
    private String username;

    @Size(min=8, message="Password must be at least 8 characters!")
    private String password;

    private String repeatPassword;
}
