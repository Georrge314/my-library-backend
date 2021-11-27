package com.bibliotek.web;

import com.bibliotek.exception.InvalidEntityException;
import com.bibliotek.model.Comment;
import com.bibliotek.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin("http://localhost:3000")
@Slf4j
public class CommentController {
    private CommentService commentService;

    @GetMapping
    public Collection<Comment> getComments() {
        return commentService.getComments();
    }

    @GetMapping("{id}")
    public Comment getComments(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @DeleteMapping("{id}")
    public Comment deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        if (comment.getId() != id) {
            throw new InvalidEntityException(
                    String.format("Comment with ID=%s from path is different from Entity ID=%s", id, comment.getId()));
        }
        Comment updated = commentService.updateComment(comment);
        log.info("Comment updated: {}...", comment.getContent().substring(0, 10));
        return ResponseEntity.ok(updated);
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        Comment created = commentService.createComment(comment);
        URI uri = MvcUriComponentsBuilder
                .fromMethodName(CommentController.class, "createComment", Comment.class)
                .pathSegment("{id}").buildAndExpand(created.getId()).toUri();
        log.info("Comment created: {}...", created.getContent().substring(0, 10));
        return ResponseEntity.created(uri).body(created);
    }
}
