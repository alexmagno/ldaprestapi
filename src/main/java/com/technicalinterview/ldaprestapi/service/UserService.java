package com.technicalinterview.ldaprestapi.service;

import com.technicalinterview.ldaprestapi.domain.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User getUser(String userId);
    User create(User user);
    User update(User user);
    User remove(String userId);
    void removeAll();
}
