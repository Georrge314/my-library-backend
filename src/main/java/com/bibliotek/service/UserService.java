package com.bibliotek.service;

import com.bibliotek.domain.model.User;

import java.util.Collection;

public interface UserService {
    User createUser(User user);

    User updateUser(User user);

    User deleteUser(Long id);

    User getUserById(Long id);

    User getUserByUsername(String username);

    Collection<User> getUsers();

    Long getUsersCount();
}
