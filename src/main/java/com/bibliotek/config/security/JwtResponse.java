package com.bibliotek.config.security;

import com.bibliotek.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    @NonNull
    @NotNull
    private String jwt;

    @NonNull
    @NotNull
    private User user;
}
