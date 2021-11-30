package com.bibliotek.domain.dto.user;

import com.bibliotek.domain.dto.book.BookSlim;
import lombok.Data;

import java.util.Set;

@Data
public class UserView {
    private Long id;

    private String username;

    private String email;

    private String fullName;

    private String imageUrl;

    private Set<BookSlim> books;
}
