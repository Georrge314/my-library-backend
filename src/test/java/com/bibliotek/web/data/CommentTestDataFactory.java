package com.bibliotek.web.data;

import com.bibliotek.domain.dto.comment.CommentView;
import com.bibliotek.domain.dto.comment.EditCommentRequest;
import com.bibliotek.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;

@Service
public class CommentTestDataFactory {
    @Autowired
    private CommentService commentService;

    public CommentView createComment(String content, Long bookId) {
        EditCommentRequest request = new EditCommentRequest();
        request.setContent(content);
        request.setBookId(bookId);

        CommentView commentView = commentService.createComment(request);
        assertNotNull(commentView.getId(), "Comment id must not be null!");
        assertEquals(commentView.getContent(), request.getContent(), "Comment update isn't applied!");

        return commentView;
    }

    public void deleteComment(Long id) {
        commentService.deleteComment(id);
    }
}
