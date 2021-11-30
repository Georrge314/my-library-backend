package com.bibliotek.domain.dto.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class CreateUserRequest {
    @NotBlank
    @Length(min = 5, max = 40)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 5, max = 40)
    private String fullName;

    @NotBlank
    @Length(min = 5, max = 40)
    private String password;

    @NotBlank
    @Length(min = 5, max = 40)
    private String rePassword;

    private Set<String> authorities;
}
