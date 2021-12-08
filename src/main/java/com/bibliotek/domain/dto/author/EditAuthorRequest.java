package com.bibliotek.domain.dto.author;

import com.bibliotek.domain.dto.book.EditBookRequest;
import com.bibliotek.domain.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
public class EditAuthorRequest {
    @NotNull
    private String fullName;

    private String nationality;

    private String imageUrl;

    private String about;

    private Set<String> genres;
}
