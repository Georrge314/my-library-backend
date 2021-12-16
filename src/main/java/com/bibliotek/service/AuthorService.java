package com.bibliotek.service;

import com.bibliotek.domain.dto.author.AuthorView;
import com.bibliotek.domain.dto.author.EditAuthorRequest;

import java.util.List;

public interface AuthorService {
    AuthorView createAuthor(EditAuthorRequest request);

    AuthorView updateAuthor(Long id, EditAuthorRequest request);

    AuthorView deleteAuthor(Long id);

    AuthorView getAuthorById(Long id);

    List<AuthorView> getBookAuthors(Long bookId);

    Long getAuthorId(String fullName);

    Long getAuthorsCount();
}
