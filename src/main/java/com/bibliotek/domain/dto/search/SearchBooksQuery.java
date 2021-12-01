package com.bibliotek.domain.dto.search;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class SearchBooksQuery {
    private Long id;

    private Long creatorId;

    private LocalDateTime createdAtStart;
    private LocalDateTime createdAtEnd;

    private String title;

    private String about;

    private String language;

    private Set<String> genres;

    private String publisher;

    private LocalDate publishedDateStart;
    private LocalDate publishedDateEnd;

    private String imageUrl;

    private String isbn13;
    private String isbn10;

    private Long authorId;
    private String authorFullName;

}
