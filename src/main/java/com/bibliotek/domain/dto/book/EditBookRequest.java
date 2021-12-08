package com.bibliotek.domain.dto.book;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class EditBookRequest {
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

    private List<Long> authorIds;
}
