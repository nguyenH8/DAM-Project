package com.dam.server.services;

import com.dam.server.dto.UserDTO;
import com.dam.server.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO getUser(Long id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(UserDTO userDTO);
    void deleteUser(Long id);
}
