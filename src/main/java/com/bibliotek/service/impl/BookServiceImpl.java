package com.bibliotek.service.impl;

import com.bibliotek.dao.AuthorRepo;
import com.bibliotek.dao.BookRepo;
import com.bibliotek.domain.dto.book.BookView;
import com.bibliotek.domain.dto.book.EditBookRequest;
import com.bibliotek.domain.mapper.BookEditMapper;
import com.bibliotek.domain.mapper.BookViewMapper;
import com.bibliotek.domain.model.Author;
import com.bibliotek.domain.model.Book;
import com.bibliotek.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private BookViewMapper viewMapper;

    @Autowired
    private BookEditMapper editMapper;

    @Override
    @Transactional
    public BookView createBook(EditBookRequest request) {
        Book book = editMapper.create(request);
        book = bookRepo.save(book);
        updateAuthors(book);

        log.info("Book with title: {} created.", book.getTitle());
        return viewMapper.toBookView(book);
    }

    private void updateAuthors(Book book) {
        List<Author> authors = authorRepo.findAllById(book.getAuthorIds());
        authors.forEach(author -> {
            author.getBookIds().add(book.getId());
            log.info("Book {} added to author {}.", book.getTitle(), author.getFullName());
        });
        authorRepo.saveAll(authors);
    }

    @Override
    @Transactional
    public BookView updateBook(Long id, EditBookRequest request) {
        Book book = bookRepo.getById(id);
        editMapper.update(request, book);

        book = bookRepo.save(book);
        log.info("Book with ID={} updated.", id);
        if (!CollectionUtils.isEmpty(request.getAuthors())) {
            updateAuthors(book);
        }

        return viewMapper.toBookView(book);
    }

    @Override
    @Transactional
    public BookView deleteBook(Long id) {
        Book book = bookRepo.getById(id);
        bookRepo.delete(book);
        log.info("Book with ID={} deleted.", id);
        return viewMapper.toBookView(book);
    }

    @Override
    public BookView getBookById(Long id) {
        Book book = bookRepo.getById(id);
        return viewMapper.toBookView(book);
    }

    @Override
    public List<BookView> getBooksByIds(Iterable<Long> ids) {
        List<Book> books = bookRepo.findAllById(ids);
        return viewMapper.toBookView(books);
    }

    @Override
    public List<BookView> getAuthorBooks(Long authorId) {
        Author author = authorRepo.getById(authorId);
        return viewMapper.toBookView(bookRepo.findAllById(author.getBookIds()));
    }

    @Override
    public Long getBooksCount() {
        return bookRepo.count();
    }
}
