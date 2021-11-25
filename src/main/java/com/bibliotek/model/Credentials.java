package com.bibliotek.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Credentials {
    @NonNull
    @NotNull
    private String username;
    @NonNull
    @NotNull
    private String password;
}
