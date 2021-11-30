package com.bibliotek.domain.dto.comment;

import lombok.Data;

@Data
public class EditCommentRequest {
    String content;

    Long likes;

    Long dislikes;
}
