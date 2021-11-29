package com.bibliotek.service.impl;

import com.bibliotek.dao.BookRepo;
import com.bibliotek.dao.UserRepo;
import com.bibliotek.domain.exception.EntityNotFoundException;
import com.bibliotek.domain.exception.InvalidEntityException;
import com.bibliotek.domain.model.User;
import com.bibliotek.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;

@Service
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BookRepo bookRepo;

    @Override
    @Transactional
    public User createUser(User user) {
        userRepo.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new InvalidEntityException(String.format("User with username: '%s' already exists.", u.getUsername()));
        });
        user.setCreated(new Date());
        user.setModified(new Date());
        if (user.getRoles() == null || user.getRoles().length() == 0) {
            user.setRoles(User.ROLE_USER);
        }
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);

        return userRepo.save(user);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        Date created = userRepo.getById(user.getId()).getCreated();
        user.setCreated(created);
        user.setModified(new Date());
        user.setRoles(User.ROLE_USER);
        user.setActive(true);
        return userRepo.save(user);
    }

    @Override
    @Transactional
    public User deleteUser(Long id) {
        User toDelete = getUserById(id);
        userRepo.deleteById(id);
        return toDelete;
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format("User with ID=%s not found.", id));
        });
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> {
           throw new EntityNotFoundException(String.format("User with username '%s' not found.", username));
        });
    }

    @Override
    public Collection<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public Long getUsersCount() {
        return userRepo.count();
    }
}
