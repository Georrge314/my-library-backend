package com.bibliotek.web;

import com.bibliotek.web.data.UserTestDataFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
class TestAuthController {

    private final MockMvc mockMvc;
    private final UserTestDataFactory userTestDataFactory;

    private String password = "test112233";

    @Autowired
    public TestAuthController(MockMvc mockMvc, UserTestDataFactory userTestDataFactory) {
        this.mockMvc = mockMvc;
        this.userTestDataFactory = userTestDataFactory;
    }

    @Test
    public void testLoginSuccsses() {

    }

    @Test
    public void testLoginFail() {

    }

    @Test void testRegisterSuccsses() {

    }

    @Test void testRegisterFail() {

    }
}