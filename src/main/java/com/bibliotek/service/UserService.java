package com.bibliotek.service;

import com.bibliotek.domain.dto.book.BookView;
import com.bibliotek.domain.dto.user.CreateUserRequest;
import com.bibliotek.domain.dto.user.UpdateUserRequest;
import com.bibliotek.domain.dto.user.UserView;
import com.bibliotek.domain.model.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    UserView createUser(CreateUserRequest request);

    UserView updateUser(Long id, UpdateUserRequest request);

    UserView upsert(CreateUserRequest request);

    UserView deleteUser(Long id);

    UserView getUserById(Long id);

    boolean usernameExists(String username);

    Long getUsersCount();

    List<BookView> getUserBooks(Long id);
}
