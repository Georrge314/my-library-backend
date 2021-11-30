package com.bibliotek.service;

import com.bibliotek.domain.dto.author.AuthorView;
import com.bibliotek.domain.dto.comment.CommentView;
import com.bibliotek.domain.dto.comment.EditCommentRequest;
import com.bibliotek.domain.model.Comment;

import java.util.Collection;
import java.util.List;

public interface CommentService {
    CommentView createComment(EditCommentRequest request);

    CommentView updateComment(Long id, EditCommentRequest request);

    CommentView deleteComment(Long id);

    CommentView getCommentById(Long id);

    List<CommentView> getComments(Iterable<Long> ids);

    List<CommentView> getBookComments(Long bookId);

    void updateBook(Comment comment);

    Long getCommentsCount();
}
