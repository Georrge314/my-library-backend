package com.bibliotek.domain.dto.comment;
import com.bibliotek.domain.dto.user.UserView;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentView {
    private Long id;

    private String content;

    private UserView creator;

    private LocalDateTime createdAt;
}
