package com.bibliotek.domain.dto.user;

import lombok.Data;

@Data
public class UserView {
    private Long id;

    private String username;

    private String fullName;

    private String imageUrl;
}
