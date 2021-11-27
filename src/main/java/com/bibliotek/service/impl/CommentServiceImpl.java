package com.bibliotek.service.impl;

import com.bibliotek.dao.CommentRepo;
import com.bibliotek.exception.EntityNotFoundException;
import com.bibliotek.model.Book;
import com.bibliotek.model.Comment;
import com.bibliotek.service.BookService;
import com.bibliotek.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private BookService bookService;

    @Override
    public Comment createComment(Comment comment) {
        Book bookById = bookService.getBookById(comment.getBook().getId());
        bookById.getComments().add(comment);
        bookService.updateBook(bookById);
        return commentRepo.save(comment);
    }

    @Override
    public Comment updateComment(Comment comment) {
        getCommentById(comment.getId());
        comment.setModified(LocalDateTime.now());
        return commentRepo.save(comment);
    }

    @Override
    public Comment deleteComment(Long id) {
        Comment commentById = getCommentById(id);
        commentRepo.deleteById(id);
        return commentById;
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepo.findById(id).orElseThrow(() -> {
           throw new EntityNotFoundException(String.format("Comment with ID=%s not found", id));
        });
    }

    @Override
    public Collection<Comment> getComments() {
        return commentRepo.findAll();
    }

    @Override
    public Long getCommentsCount() {
        return commentRepo.count();
    }
}
