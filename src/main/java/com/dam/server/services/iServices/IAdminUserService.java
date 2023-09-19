package com.dam.server.services.iServices;

import com.dam.server.entities.User;

import java.util.List;

public interface IAdminUserService {
    User createUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
