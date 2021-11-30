package com.bibliotek.service;

import com.bibliotek.domain.dto.book.BookView;
import com.bibliotek.domain.dto.book.EditBookRequest;


import java.util.List;

public interface BookService {
    BookView createBook(EditBookRequest request);

    BookView updateBook(Long id, EditBookRequest request);

    BookView deleteBook(Long id);

    BookView getBookById(Long id);

    List<BookView> getBooksByIds(Iterable<Long> ids);

    List<BookView> getAuthorBooks(Long authorId);

    Long getBooksCount();
}
