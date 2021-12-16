package com.bibliotek.service.impl;

import com.bibliotek.dao.UserRepo;
import com.bibliotek.domain.dto.book.BookView;
import com.bibliotek.domain.dto.user.CreateUserRequest;
import com.bibliotek.domain.dto.user.UpdateUserRequest;
import com.bibliotek.domain.dto.user.UserView;
import com.bibliotek.domain.exception.EntityNotFoundException;
import com.bibliotek.domain.exception.InvalidEntityException;
import com.bibliotek.domain.mapper.BookViewMapper;
import com.bibliotek.domain.mapper.UserEditMapper;
import com.bibliotek.domain.mapper.UserViewMapper;
import com.bibliotek.domain.model.Book;
import com.bibliotek.domain.model.User;
import com.bibliotek.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserViewMapper viewMapper;
    @Autowired
    private UserEditMapper editMapper;
    @Autowired
    private BookViewMapper bookViewMapper;


    @Transactional
    @Override
    public UserView createUser(CreateUserRequest request) {
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new InvalidEntityException(
                    String.format("User with username: %s already exists.", request.getUsername()));
        }

        if (!request.getPassword().equals(request.getRePassword())) {
            throw new InvalidEntityException("Passwords don't match!");
        }

        if (request.getAuthorities() == null) {
            request.setAuthorities(new HashSet<>());
        }

        User user = editMapper.create(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userRepo.save(user);
        log.info("User with username: {} created.", user.getUsername());
        return viewMapper.toUserView(user);
    }

    @Transactional
    @Override
    public UserView updateUser(Long id, UpdateUserRequest request) {
        User user = userRepo.getById(id);
        editMapper.update(request, user);

        user = userRepo.save(user);

        log.info("User with ID={} updated.", id);
        return viewMapper.toUserView(user);
    }

    @Transactional
    @Override
    public UserView upsert(CreateUserRequest request) {
        Optional<User> optionalUser = userRepo.findByUsername(request.getUsername());
        if (optionalUser.isEmpty()) {
            return createUser(request);
        }
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setUsername(request.getUsername());
        updateUserRequest.setEmail(request.getEmail());
        updateUserRequest.setFullName(request.getFullName());
        return updateUser(optionalUser.get().getId(), updateUserRequest);
    }

    @Transactional
    @Override
    public UserView deleteUser(Long id) {
        User user = userRepo.getById(id);

        user.setUsername(user.getUsername().replace("@", String.format("_%s@", id)));
        user.setActive(false);
        userRepo.save(user);

        log.info("User with ID={} was deactiveted", id);
        return viewMapper.toUserView(user);
    }

    @Override
    public UserView getUserById(Long id) {
        User user = userRepo.getById(id);
        if (!user.isActive()) {
            throw new EntityNotFoundException(String.format("User with ID=%s is not active", id));
        }
        return viewMapper.toUserView(user);
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepo.findByUsername(username).isPresent();
    }

    @Override
    public Long getUsersCount() {
        return userRepo.count();
    }

    @Override
    public List<BookView> getUserBooks(Long id) {
        Set<Book> books = userRepo.getById(id).getBooks();
        return bookViewMapper.toBookView(books);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo
                .findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("User with username: %s not found.", username)
                        ));
    }
}
