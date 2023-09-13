package com.dam.server.services.iServices;

import com.dam.server.models.User;
import com.dam.server.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUser(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
