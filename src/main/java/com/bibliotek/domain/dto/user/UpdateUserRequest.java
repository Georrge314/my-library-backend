package com.bibliotek.domain.dto.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class UpdateUserRequest {
    @NotNull
    private String username;

    private String password;

    private String email;

    private String fullName;

    private String imageUrl;

    private Set<String> authorities;

    private Set<Long> bookIds;
}
