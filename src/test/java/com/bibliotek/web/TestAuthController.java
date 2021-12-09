package com.bibliotek.web;

import com.bibliotek.domain.dto.Credentials;
import com.bibliotek.domain.dto.user.CreateUserRequest;
import com.bibliotek.domain.dto.user.UserView;
import com.bibliotek.web.data.UserTestDataFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.bibliotek.util.JsonHelper.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TestAuthController {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final UserTestDataFactory userTestDataFactory;

    private String password = "test112233";

    @Autowired
    public TestAuthController(MockMvc mockMvc, ObjectMapper objectMapper, UserTestDataFactory userTestDataFactory) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.userTestDataFactory = userTestDataFactory;
    }

    @Test
    public void testLoginSuccsses() throws Exception {
        UserView userView = userTestDataFactory.createUser(
                "test@bs.io",
                "test@gmail.com",
                "Test Testov",
                password);//optional

        Credentials credentials = new Credentials();
        credentials.setUsername(userView.getUsername());
        credentials.setPassword(password);

        MvcResult createResult = this.mockMvc
                .perform(post("/api/public/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, credentials)))
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.AUTHORIZATION))
                .andReturn();

        UserView authUserView = fromJson(objectMapper, createResult.getResponse().getContentAsString(), UserView.class);
        assertEquals(userView.getId(), authUserView.getId(), "User ids must match!");
    }

    @Test
    public void testLoginFail() throws Exception {
        UserView userView = userTestDataFactory.createUser(
                "testUser@bs.io",
                "usetTest@gmail.com",
                "Test Testov",
                password);

        Credentials credentials = new Credentials();
        credentials.setUsername(userView.getUsername());
        credentials.setPassword("atlz"); //must be min 5 symbols

        MvcResult createResult = this.mockMvc
                .perform(post("/api/public/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, credentials)))
                .andExpect(status().isUnauthorized())
                .andExpect(header().doesNotExist(HttpHeaders.AUTHORIZATION))
                .andReturn();
    }

    @Test
    void testRegisterSuccsses() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("newuser123@bs.io");
        request.setEmail("testUser@gmail.com");
        request.setFullName("Test Testov");
        request.setPassword(password);
        request.setRePassword(password);

        MvcResult createResult = this.mockMvc
                .perform(post("/api/public/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isOk())
                .andReturn();

        UserView userView = fromJson(objectMapper, createResult.getResponse().getContentAsString(), UserView.class);
        assertNotNull(userView.getId(), "User id must not be null!");
        assertEquals(userView.getEmail(), request.getEmail(), "User email update isn't applied!");
    }

    @Test
    void testRegisterFail() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("newprofil@bs.io");
        request.setEmail("testUser@gmail.com");
        request.setFullName("Test Testov");
        // password is blanck

        this.mockMvc
                .perform(post("/api/public/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Method argument validation failed")));
    }
}