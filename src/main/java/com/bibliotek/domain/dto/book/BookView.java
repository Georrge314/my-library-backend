package com.bibliotek.domain.dto.book;

import com.bibliotek.domain.dto.comment.CommentView;
import com.bibliotek.domain.dto.user.UserView;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class BookView {

    private Long id;

    private UserView creator;

    private LocalDateTime createdAt;

    private String title;

    private String about;

    private String language;

    private List<String> genres;

    private LocalDate publishedDate;

    private String imageUrl;

    private String isbn13;

    private String isbn10;

    private String publisher;

    private Double rating;

    private Set<CommentView> comments;
}
