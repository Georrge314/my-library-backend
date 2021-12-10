package com.bibliotek.domain.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class EditCommentRequest {
    @NotBlank
    @NotNull
    private String content;

    @NotNull
    private Long bookId;
}
