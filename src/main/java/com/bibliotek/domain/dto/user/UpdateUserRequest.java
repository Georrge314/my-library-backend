package com.bibliotek.domain.dto.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateUserRequest {
    @NotNull
    private String username;

    private String email;

    private String fullName;

}
