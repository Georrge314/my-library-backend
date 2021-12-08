package com.bibliotek.domain.dto.author;

import com.bibliotek.domain.model.Genre;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class EditAuthorRequest {

    @NotNull
    private String fullName;

    private String nationality;

    private String imageUrl;

    private String about;

    private Set<Genre> genres;
}
