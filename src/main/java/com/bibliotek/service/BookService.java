package com.bibliotek.service;

import com.bibliotek.model.Book;

import java.util.Collection;

public interface BookService {
    Book createBook(Book book);

    Book updateBook(Book book);

    Book deleteBook(Long id);

    Book getBookById(Long id);

    Book getBookByTitle(String title);

    Collection<Book> getBooks();

    Long getBooksCount();
}
