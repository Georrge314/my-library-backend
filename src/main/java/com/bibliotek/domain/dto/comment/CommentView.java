package com.bibliotek.domain.dto.comment;
import com.bibliotek.domain.dto.user.UserView;
import lombok.Data;

@Data
public class CommentView {
    Long id;

    String content;

    UserView creator;
}
