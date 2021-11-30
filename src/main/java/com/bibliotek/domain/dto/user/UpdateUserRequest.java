package com.bibliotek.domain.dto.user;

import lombok.Data;

import java.util.Set;

@Data
public class UpdateUserRequest {

    private String username;

    private String password;

    private String email;

    private String fullName;

    private String imageUrl;

    private Set<String> authorities;
}
