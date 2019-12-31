package com.technicalinterview.ldaprestapi.service;

import com.technicalinterview.ldaprestapi.domain.User;
import com.technicalinterview.ldaprestapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements  UserService{

    @Autowired
    UserRepository userRepository;


    @Override
    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    @Override
    public User getUser(String userId) {
        return userRepository.getOne(userId);
    }

    @Override
    public User create(User user) {
        return userRepository.create(user);
    }

    @Override
    public User update(User user) {
        return userRepository.update(user);
    }

    @Override
    public User remove(String userId) {
        return userRepository.remove(userId);
    }

    @Override
    public void removeAll() {
        userRepository.removeAll();
    }
}
