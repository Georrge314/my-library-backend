package com.bibliotek.service.impl;

import com.bibliotek.dao.UserRepo;
import com.bibliotek.domain.dto.user.CreateUserRequest;
import com.bibliotek.domain.dto.user.UpdateUserRequest;
import com.bibliotek.domain.dto.user.UserView;
import com.bibliotek.domain.exception.InvalidEntityException;
import com.bibliotek.domain.mapper.UserEditMapper;
import com.bibliotek.domain.mapper.UserViewMapper;
import com.bibliotek.domain.model.User;
import com.bibliotek.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserViewMapper viewMapper;
    @Autowired
    private UserEditMapper editMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


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

        return viewMapper.toUserView(user);
    }

    @Override
    public UserView getUserById(Long id) {
        return viewMapper.toUserView(userRepo.getById(id));
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo
                .findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("User with username: %s not found.", username)
                        ));
    }

    //TODO: impl list of users
//    public List<UserView> searchUsers(Page page, SearchUsersQuery query) {
//        List<User> users = userRepo.searchUsers(page, query);
//        return userViewMapper.toUserView(users);
//    }
}
