package com.bibliotek.domain.dto.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class UpdateUserRequest {
    @NotNull
    private String username;

    private String email;

    private String fullName;

    private Set<String> authorities;
}
