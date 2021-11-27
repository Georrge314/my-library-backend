package com.bibliotek.service;

import com.bibliotek.model.Comment;

import java.util.Collection;

public interface CommentService {
    Comment createComment(Comment comment);

    Comment updateComment(Comment comment);

    Comment deleteComment(Long id);

    Comment getCommentById(Long id);

    Collection<Comment> getComments();

    Long getCommentsCount();
}
