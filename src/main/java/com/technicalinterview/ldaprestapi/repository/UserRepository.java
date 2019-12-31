package com.technicalinterview.ldaprestapi.repository;

import com.technicalinterview.ldaprestapi.domain.User;

import java.util.List;

public interface UserRepository {

    List<User> getAll();
    User getOne(String userId);
    User create(User p);
    User update(User p);
    User remove(String userId);
    void removeAll();
    }
