package com.bibliotek.domain.dto.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class UpdateUserRequest {
    @Length(min = 5, max = 40)
    private String username;

    @Email
    private String email;

    @Length(min = 5, max = 40)
    private String fullName;

    private Set<String> authorities;
}
