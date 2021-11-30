package com.bibliotek.domain.dto.comment;
import com.bibliotek.domain.dto.user.UserView;
import lombok.Data;

@Data
public class CommentView {
    Long id;

    UserView creator;

    String content;

    Long likes;

    Long dislikes;
}
