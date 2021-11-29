package com.bibliotek.service.impl;

import com.bibliotek.dao.BookRepo;
import com.bibliotek.dao.UserRepo;
import com.bibliotek.domain.exception.EntityNotFoundException;
import com.bibliotek.domain.exception.InvalidEntityException;
import com.bibliotek.domain.model.Author;
import com.bibliotek.domain.model.Book;
import com.bibliotek.domain.model.User;
import com.bibliotek.service.AuthorService;
import com.bibliotek.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired AuthorService authorService;

    @Override
    @Transactional
    public Book createBook(Book book) {
        try {
            getBookByTitle(book.getTitle());
            throw new InvalidEntityException(
                    String.format("Book with title '%s' already exists.", book.getTitle()));
        } catch (EntityNotFoundException exception) {

            try {
                Author authorByFullName = authorService.getAuthorByFullName(book.getAuthor().getFullName());
                book.setAuthor(authorByFullName);
            } catch (EntityNotFoundException ignored) { }
            return bookRepo.save(book);
        }
    }

    @Override
    @Transactional
    public Book updateBook(Book book) {
        System.out.println(book.getAuthor());
        LocalDateTime created = getBookById(book.getId()).getCreated();

        book.setModified(LocalDateTime.now());
        book.setCreated(created);

        try {
            Author authorByFullName = authorService.getAuthorByFullName(book.getAuthor().getFullName());
            book.setAuthor(authorByFullName);
        } catch (EntityNotFoundException ex) {
            authorService.createAuthor(book.getAuthor());
            Author persistedAuthor = authorService.getAuthorByFullName(book.getAuthor().getFullName());
            book.setAuthor(persistedAuthor);
        }

        return bookRepo.save(book);
    }


    @Override
    @Transactional
    public Book deleteBook(Long id) {
        Book toDelete = getBookById(id);

        Set<User> users = toDelete.getUsers();
        for (User user : users) {
            user.removeBook(toDelete);
            userRepo.save(user);
        }

        bookRepo.deleteById(id);
        return toDelete;
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepo.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format("Book with ID=%s not found", id));
        });
    }

    @Override
    public Book getBookByTitle(String title) {
        return bookRepo.findByTitle(title).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format("Book with title '%s' not found", title));
        });
    }

    @Override
    public List<Book> getBooks() {
        return bookRepo.findAll();
    }

    @Override
    public Long getBooksCount() {
        return bookRepo.count();
    }
}
