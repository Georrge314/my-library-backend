package com.bibliotek.domain.dto.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditAuthorRequest {
    @NotNull
    private String fullName;

    private String nationality;

    private String imageUrl;

    private String about;

    private Set<String> genres;
}
