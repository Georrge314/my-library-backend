package com.bibliotek.domain.dto.author;

import com.bibliotek.domain.dto.user.UserView;
import com.bibliotek.domain.model.Genre;
import lombok.Data;

import java.util.Set;

@Data
public class AuthorView {
    private Long id;

    private UserView creator;

    private String fullName;

    private String nationality;

    private String imageUrl;

    private String about;

    private Set<Genre> genres;
}
