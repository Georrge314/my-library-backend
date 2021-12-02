package com.bibliotek.domain.mapper;

import com.bibliotek.domain.dto.comment.CommentView;
import com.bibliotek.domain.model.Comment;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CommentViewMapper {
    @Autowired
    private UserViewMapper userViewMapper;

    public abstract CommentView toCommentView(Comment comment);

    public abstract List<CommentView> toCommentView(List<Comment> comments);

    @AfterMapping
    protected void after(Comment comment, @MappingTarget CommentView commentView) {
        commentView.setCreator(userViewMapper.toUserViewById(comment.getCreator().getId()));
    }
}
