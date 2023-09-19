package com.dam.server.services.impl;

import com.dam.server.entities.User;
import com.dam.server.repositories.UserRepository;
import com.dam.server.services.iServices.IAdminUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AdminUserServiceImpl implements IAdminUserService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        if(user == null){
            throw new IllegalArgumentException("User cannot be null");
        }

        if(userRepository.existsByUsername(user.getUsername())){
            throw new IllegalArgumentException("A user with the same username already exists");
        }

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        if(id == null){
            throw new IllegalArgumentException("ID cannot be null");
        }

        return userRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("User with ID " + id + " not found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {
        if (id == null || user == null){
            throw new IllegalArgumentException("Invalid user or user ID");
        }
        Optional<User> existingUser = userRepository.findById(id);
        if(existingUser.isEmpty()){
            throw new NoSuchElementException("User with ID " + id + " not found");
        }

        User userToUpdate = existingUser.get();
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setEmail(user.getEmail());

        return userRepository.save(userToUpdate);
    }

    @Override
    public void deleteUser(Long id) {
        if (id == null || !userRepository.existsById(id)) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        userRepository.deleteById(id);
    }
}
