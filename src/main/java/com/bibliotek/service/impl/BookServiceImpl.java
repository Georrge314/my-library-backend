package com.bibliotek.service.impl;

import com.bibliotek.dao.BookRepo;
import com.bibliotek.dao.UserRepo;
import com.bibliotek.exception.EntityNotFoundException;
import com.bibliotek.exception.InvalidEntityException;
import com.bibliotek.model.Book;
import com.bibliotek.model.User;
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

    @Override
    @Transactional
    public Book createBook(Book book) {
        try {
            getBookByTitle(book.getTitle());
            throw new InvalidEntityException(
                    String.format("Book with title '%s' already exists.", book.getTitle()));
        } catch (EntityNotFoundException exception) {
            book.setCreated(LocalDateTime.now());
            book.setModified(LocalDateTime.now());
            return bookRepo.save(book);
        }
    }

    @Override
    @Transactional
    public Book updateBook(Book book) {
        book.setModified(LocalDateTime.now());
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
