package com.bibliotek.service;

import com.bibliotek.domain.model.Author;

import java.util.Collection;

public interface AuthorService {
    Author createAuthor(Author author);

    Author updateAuthor(Author author);

    Author deleteAuthor(Long id);

    Author getAuthorById(Long id);

    Author getAuthorByFullName(String fullName);

    Collection<Author> getAuthors();

    Long getAuthorsCount();
}
