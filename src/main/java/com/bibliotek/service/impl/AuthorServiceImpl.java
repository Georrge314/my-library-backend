package com.bibliotek.service.impl;

import com.bibliotek.dao.AuthorRepo;
import com.bibliotek.model.Author;
import com.bibliotek.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepo authorRepo;

    @Override
    public Author createAuthor(Author author) {
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {
        return null;
    }

    @Override
    public Author deleteAuthor(Long id) {
        return null;
    }

    @Override
    public Author getAuthorById(Long id) {
        return null;
    }

    @Override
    public Author getAuthorByFullName(String fullName) {
        return null;
    }


    @Override
    public Collection<Author> getAuthors() {
        return null;
    }

    @Override
    public Long getAuthorsCount() {
        return null;
    }
}
