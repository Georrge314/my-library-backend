package com.bibliotek.web.data;

import com.bibliotek.domain.dto.book.BookView;
import com.bibliotek.domain.dto.book.EditBookRequest;
import com.bibliotek.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Service
public class BookTestDataFactory {
    @Autowired
    public BookService bookService;

    public BookView createBook(
            String title,
            List<Long> authorIds,
            String about,
            String language,
            Set<String> genres,
            LocalDate publishedDate,
            String imageUrl,
            String isbn13,
            String isbn10,
            String publisher) {

        EditBookRequest request = new EditBookRequest();
        request.setTitle(title);
        request.setAbout(about);
        request.setLanguage(language);
        request.setGenres(genres);
        request.setPublishedDate(publishedDate);
        request.setImageUrl(imageUrl);
        request.setIsbn13(isbn13);
        request.setIsbn10(isbn10);
        request.setPublisher(publisher);
        request.setAuthorIds(authorIds);

        BookView bookView = bookService.createBook(request);

        assertNotNull(bookView.getId(), "Book id must not be null!");
        assertEquals(title, bookView.getTitle(), "Book title update isn't applied!");

        return bookView;
    }

    public BookView createBook(
            String title,
            List<Long> authorIds,
            String about,
            String language,
            Set<String> genres,
            LocalDate publishedDate,
            String imageUrl,
            String isbn13,
            String isbn10) {
        return createBook(title, authorIds, about, language, genres, publishedDate, imageUrl, isbn13, isbn10,
                null);
    }

    public BookView createBook(
            String title,
            List<Long> authorIds,
            String about,
            String language,
            Set<String> genres,
            LocalDate publishedDate,
            String imageUrl,
            String isbn13) {
        return createBook(title, authorIds, about, language, genres, publishedDate, imageUrl, isbn13,
                null, null);
    }

    public BookView createBook(
            String title,
            List<Long> authorIds,
            String about,
            String language,
            Set<String> genres,
            LocalDate publishedDate,
            String imageUrl) {
        return createBook(title, authorIds, about, language, genres, publishedDate, imageUrl, null,
                null, null);
    }

    public BookView createBook(
            String title,
            List<Long> authorIds,
            String about,
            String language,
            Set<String> genres,
            LocalDate publishedDate) {
        return createBook(title, authorIds, about, language, genres, publishedDate, null,
                null, null, null);
    }

    public BookView createBook(
            String title,
            List<Long> authorIds,
            String about,
            String language,
            Set<String> genres) {
        return createBook(title, authorIds, about, language, genres, null, null, null,
                null, null);
    }

    public BookView createBook(
            String title,
            List<Long> authorIds,
            String about,
            String language) {
        return createBook(title, authorIds, about, language, null, null, null,
                null, null, null);
    }

    public BookView createBook(
            String title,
            List<Long> authorIds,
            String about) {
        return createBook(title, authorIds, about, null, null, null,
                null, null, null, null);
    }

    public BookView createBook(
            String title,
            List<Long> authorIds) {
        return createBook(title, authorIds, null, null, null, null,
                null, null, null, null);
    }

    public void delteBook(Long id) {
        bookService.deleteBook(id);
    }
}
