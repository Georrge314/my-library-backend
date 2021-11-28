package com.bibliotek.service.impl;

import com.bibliotek.dao.AuthorRepo;
import com.bibliotek.exception.EntityNotFoundException;
import com.bibliotek.exception.InvalidEntityException;
import com.bibliotek.model.Author;
import com.bibliotek.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepo authorRepo;

    @Override
    public Author createAuthor(Author author) {
        try {
            getAuthorByFullName(author.getFullName());
            throw new InvalidEntityException(String.format("Author with name: %s already exists.", author.getFullName()));
        } catch (EntityNotFoundException exception) {
            return authorRepo.save(author);
        }
    }

    @Override
    public Author updateAuthor(Author author) {
        //TODO: impl
        return null;
    }

    @Override
    public Author deleteAuthor(Long id) {
        Author deleted = getAuthorById(id);
        authorRepo.deleteById(id);
        return deleted;
    }

    @Override
    public Author getAuthorById(Long id) {
        return authorRepo.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format("Author with ID=%s not found.", id));
        });
    }

    @Override
    public Author getAuthorByFullName(String fullName) {
        return authorRepo.findByFullName(fullName).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format("Author with name: %s not found", fullName));
        });
    }


    @Override
    public Collection<Author> getAuthors() {
        return authorRepo.findAll();
    }

    @Override
    public Long getAuthorsCount() {
        return authorRepo.count();
    }
}
