package com.bibliotek.web.data;

import com.bibliotek.domain.dto.user.CreateUserRequest;
import com.bibliotek.domain.dto.user.UserView;
import com.bibliotek.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;

@Service
public class UserTestDataFactory {
    @Autowired
    private UserService userService;

    public UserView createUser(String username,
                               String email,
                               String fullName,
                               String password) {

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername(username);
        createUserRequest.setEmail(email);
        createUserRequest.setFullName(fullName);
        createUserRequest.setPassword(password);
        createUserRequest.setRePassword(password);

        UserView userView = userService.createUser(createUserRequest);

        assertNotNull(userView.getId(), "User id must not be null!");
        assertEquals(fullName, userView.getFullName(), "User name update isn't applied!");

        return userView;
    }

    public UserView createUser(String username,
                               String email,
                               String fullName) {
        return createUser(username, email, fullName, "test112233");
    }

    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }
}
