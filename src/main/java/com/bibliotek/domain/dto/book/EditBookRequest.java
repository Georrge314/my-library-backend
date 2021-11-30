package com.bibliotek.domain.dto.book;

import com.bibliotek.domain.dto.comment.CommentView;
import com.bibliotek.domain.dto.user.UserView;
import com.bibliotek.domain.model.Author;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class EditBookRequest {
    private List<Author> authors;

    @NotNull
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
