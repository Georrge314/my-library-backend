package com.bibliotek.web;

import com.bibliotek.domain.dto.comment.CommentView;
import com.bibliotek.domain.dto.comment.EditCommentRequest;
import com.bibliotek.domain.model.Role;
import com.bibliotek.service.BookService;
import com.bibliotek.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin("http://localhost:3000")
public class CommentController {
    @Autowired
    private CommentService commentService;


    @RolesAllowed(Role.COMMENT_ADMIN)
    @PostMapping
    public CommentView createComment(@RequestBody @Valid EditCommentRequest request) {
        return commentService.createComment(request);
    }

    @RolesAllowed(Role.COMMENT_ADMIN)
    @PutMapping("{id}")
    public CommentView editComment(@PathVariable Long id, @RequestBody @Valid EditCommentRequest request) {
        return commentService.updateComment(id, request);
    }

    @RolesAllowed(Role.COMMENT_ADMIN)
    @DeleteMapping("{id}")
    public CommentView deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }

    @GetMapping("{id}")
    public CommentView getComment(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    //TODO impl search method
}
