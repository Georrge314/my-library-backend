package com.bibliotek.service.impl;

import com.bibliotek.dao.AuthorRepo;
import com.bibliotek.dao.BookRepo;
import com.bibliotek.domain.dto.author.AuthorView;
import com.bibliotek.domain.dto.author.EditAuthorRequest;
import com.bibliotek.domain.mapper.AuthorEditMapper;
import com.bibliotek.domain.mapper.AuthorViewMapper;
import com.bibliotek.domain.model.Author;
import com.bibliotek.domain.model.Book;
import com.bibliotek.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepo authorRepo;
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private AuthorEditMapper editMapper;
    @Autowired
    private AuthorViewMapper viewMapper;

    @Override
    @Transactional
    public AuthorView createAuthor(EditAuthorRequest request) {
        Author author = editMapper.create(request);

        author = authorRepo.save(author);

        log.info("Author with name: {} created.", request.getFullName());
        return viewMapper.toAuthorView(author);
    }


    @Override
    @Transactional
    public AuthorView updateAuthor(Long id, EditAuthorRequest request) {
        Author author = authorRepo.getById(id);
        editMapper.update(request, author);

        author = authorRepo.save(author);
        log.info("Author with ID={} updated.", author.getId());

        return viewMapper.toAuthorView(author);
    }

    @Override
    @Transactional
    public AuthorView deleteAuthor(Long id) {
        Author author = authorRepo.getById(id);

        authorRepo.delete(author);
        bookRepo.deleteAll(bookRepo.findAllById(author.getBooks()
                .stream().map(Book::getId).collect(Collectors.toList())));

        log.info("Author with ID={} deleted", author.getId());
        return viewMapper.toAuthorView(author);
    }

    @Override
    public AuthorView getAuthorById(Long id) {
        Author author = authorRepo.getById(id);
        return viewMapper.toAuthorView(author);
    }

//    @Override
//    public List<AuthorView> getAuthors(Iterable<Long> ids) {
//        return viewMapper.toAuthorView(authorRepo.findAllById(ids));
//    }

    @Override
    public List<AuthorView> getBookAuthors(Long bookId) {
        Book book = bookRepo.getById(bookId);
        Set<Author> authors = book.getAuthors();
        return viewMapper.toAuthorView(authors);
    }

    @Override
    public Long getAuthorsCount() {
        return authorRepo.count();
    }
}
