package com.bibliotek.domain.dto.search;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class SerachAuthorsQuery {
    private Long id;

    private String fullName;

    private Long creatorId;

    private LocalDateTime createdAtStart;
    private LocalDateTime createdAtEnd;

    private Set<String> genres;

    private Long bookId;
    private String bookTitle;
}
