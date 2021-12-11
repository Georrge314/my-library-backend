package com.bibliotek.web;

import com.bibliotek.domain.dto.user.CreateUserRequest;
import com.bibliotek.domain.dto.user.UpdateUserRequest;
import com.bibliotek.domain.dto.user.UserView;
import com.bibliotek.web.data.UserTestDataFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.bibliotek.util.JsonHelper.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("gesh.petrov@bs.io")
class TestUserAdminController {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final UserTestDataFactory userTestDataFactory;

    @Autowired
    TestUserAdminController(MockMvc mockMvc, ObjectMapper objectMapper, UserTestDataFactory userTestDataFactory) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.userTestDataFactory = userTestDataFactory;
    }

    @Test
    public void testCreateSuccess() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername(String.format("validUser%s@bs.io", System.currentTimeMillis()));
        request.setEmail("validEmial@gmail.com");
        request.setFullName("Valid User");
        request.setPassword("validPass");
        request.setRePassword("validPass");

        MvcResult createResult = mockMvc
                .perform(post("/api/admin/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isOk())
                .andReturn();

        UserView userView = fromJson(objectMapper, createResult.getResponse().getContentAsString(), UserView.class);
        assertNotNull(userView.getId(), "User id must not be null!");
        assertEquals(userView.getFullName(), request.getFullName(), "User fullname update is not applied!");
    }

    @Test
    public void testCreateFail() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername(String.format("validUser%s@bs.io", System.currentTimeMillis()));

        mockMvc
                .perform(post("/api/admin/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Method argument validation failed")));
    }

    @Test
    public void testCreateUsernameExists() throws Exception {
        UserView userView = userTestDataFactory.createUser(
                String.format("validUser%s@bs.io", System.currentTimeMillis()),
                "validEmial@gmail.com",
                "Valid User",
                "test112233");

        CreateUserRequest request = new CreateUserRequest();
        request.setUsername(userView.getUsername()); // already exists
        request.setFullName("Someone Someone");
        request.setEmail("someone@gmai.com");
        request.setPassword("test112233");
        request.setRePassword("test112233");

        mockMvc
                .perform(post("/api/admin/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString
                        (String.format("User with username: %s already exists.", userView.getUsername()))));
    }

    @Test
    public void testCreatePasswordsMismatch() throws Exception {
        CreateUserRequest badRequest = new CreateUserRequest();
        badRequest.setUsername(String.format("validUser%s@bs.io", System.currentTimeMillis()));
        badRequest.setEmail("validemal@gmai.com");
        badRequest.setFullName("Test User");
        badRequest.setPassword("test12345_");
        badRequest.setRePassword("test12345");

        this.mockMvc
                .perform(post("/api/admin/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, badRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Passwords don't match!")));;
        ;
    }

    @Test
    public void testEditSuccsses() throws Exception {
        UserView userView = userTestDataFactory.createUser(
                String.format("validUser%s@bs.io", System.currentTimeMillis()),
                "validemail@gmail.com",
                "Valid User");

        UpdateUserRequest request = new UpdateUserRequest();
        request.setFullName("New Valid User");

        MvcResult createResult = this.mockMvc
                .perform(put(String.format("/api/admin/user/%s", userView.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isOk())
                .andReturn();

        UserView newUserView = fromJson(objectMapper, createResult.getResponse().getContentAsString(), UserView.class);
        assertEquals(newUserView.getFullName(), request.getFullName(), "User fullname update isn't applied!");
    }

    @Test
    public void testEditFailBadRequest() throws Exception {
        UserView userView = userTestDataFactory.createUser(
                "validUser@bs.io",
                "validemail@gmail.com",
                "Valid User");

        UpdateUserRequest request = new UpdateUserRequest();
        request.setUsername("Some");// must be at least 5 symbols

        this.mockMvc
                .perform(put(String.format("/api/admin/user/%s", userView.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Method argument validation failed")));
    }

    @Test
    public void testEditFailNotFound() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setUsername(String.format("validUser%s@bs.io", System.currentTimeMillis()));

        this.mockMvc
                .perform(put(String.format("/api/admin/user/%s", 314))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity not found exception")));;
    }

    @Test
    public void testDeleteSuccsses() throws Exception {
        UserView userView = userTestDataFactory.createUser(
                String.format("validUser%s@bs.io", System.currentTimeMillis()),
                "validemail@gmail.com",
                "Valid User");

        this.mockMvc
                .perform(delete(String.format("/api/admin/user/%s", userView.getId())))
                .andExpect(status().isOk());

        this.mockMvc
                .perform(get(String.format("/api/admin/user/%s", userView.getId())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteFailNotFound() throws Exception {
        this.mockMvc
                .perform(delete(String.format("/api/admin/user/%s", "314")))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity not found exception")));
    }

    @Test
    public void testDeleteAndCreateAgain() throws Exception {
        String username = String.format("validUser%s@bs.io", System.currentTimeMillis());
        String email = "validemail@gmail.com";
        String fullName = "Valid User";
        String password = "test112233";

        UserView userView = userTestDataFactory.createUser(
                username,
                email,
                fullName,
                password
        );

        this.mockMvc
                .perform(delete(String.format("/api/admin/user/%s", userView.getId())))
                .andExpect(status().isOk());

        this.mockMvc
                .perform(get(String.format("/api/admin/user/%s", userView.getId())))
                .andExpect(status().isNotFound());

        CreateUserRequest request = new CreateUserRequest();
        request.setUsername(username);
        request.setEmail(email);
        request.setFullName(fullName);
        request.setPassword(password);
        request.setRePassword(password);

        MvcResult createResult = this.mockMvc
                .perform(post("/api/admin/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isOk())
                .andReturn();

        UserView newUserView = fromJson(objectMapper, createResult.getResponse().getContentAsString(), UserView.class);
        assertNotEquals(userView.getId(), newUserView.getId(), "User ids must not match!");
        assertEquals(userView.getUsername(), newUserView.getUsername(), "User usernames musth match!");
    }

    @Test
    public void testGetSuccsses() throws Exception {
        UserView userView = userTestDataFactory.createUser(
                String.format("validUser%s@bs.io", System.currentTimeMillis()),
                "validemail@gmail.com",
                "Valid User");

        MvcResult createResult = this.mockMvc
                .perform(get(String.format("/api/admin/user/%s", userView.getId())))
                .andExpect(status().isOk())
                .andReturn();

        UserView newUserView = fromJson(objectMapper, createResult.getResponse().getContentAsString(), UserView.class);
        assertEquals(userView.getId(), newUserView.getId(), "User ids musth match!");
    }

    @Test
    public void testGetFailNotFound() throws Exception {
        this.mockMvc
                .perform(get(String.format("/api/admin/user/%s", 314)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity not found exception")));
    }

}