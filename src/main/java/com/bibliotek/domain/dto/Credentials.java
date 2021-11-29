package com.bibliotek.web;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credentials {
    @NonNull
    @NotNull
    private String username;
    @NonNull
    @NotNull
    private String password;
}
