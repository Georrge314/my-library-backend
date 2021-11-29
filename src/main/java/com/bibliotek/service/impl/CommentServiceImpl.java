package com.bibliotek.service.impl;

import com.bibliotek.dao.CommentRepo;
import com.bibliotek.domain.exception.EntityNotFoundException;
import com.bibliotek.domain.model.Book;
import com.bibliotek.domain.model.Comment;
import com.bibliotek.domain.model.User;
import com.bibliotek.service.BookService;
import com.bibliotek.service.CommentService;
import com.bibliotek.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@Slf4j
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public Comment createComment(Comment comment) {
        Long creatorId = comment.getCreatorId();
        User user = userService.getUserById(creatorId);
        comment.setCreator(user);

        Long bookId = comment.getBookId();
        Book book = bookService.getBookById(bookId);
        book.getComments().add(comment);
        comment.setBook(book);

        return commentRepo.save(comment);
    }

    @Override
    @Transactional
    public Comment updateComment(Comment comment) {
        LocalDateTime created = getCommentById(comment.getId()).getCreated();
        comment.setCreated(created);
        comment.setModified(LocalDateTime.now());
        return commentRepo.save(comment);
    }

    @Override
    @Transactional
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
