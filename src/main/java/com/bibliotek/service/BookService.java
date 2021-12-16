package com.bibliotek.service;

import com.bibliotek.domain.dto.Page;
import com.bibliotek.domain.dto.book.BookView;
import com.bibliotek.domain.dto.book.EditBookRequest;
import com.bibliotek.domain.dto.search.SearchBooksQuery;


import java.util.List;

public interface BookService {
    BookView createBook(EditBookRequest request);

    BookView updateBook(Long id, EditBookRequest request);

    BookView deleteBook(Long id);

    BookView getBookById(Long id);

    List<BookView> getAuthorBooks(Long authorId);

    Long getBooksCount();

    List<BookView> searchBooks(Page page, SearchBooksQuery query);
}
