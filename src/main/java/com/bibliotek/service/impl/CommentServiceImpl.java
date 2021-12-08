package com.bibliotek.service.impl;

import com.bibliotek.dao.BookRepo;
import com.bibliotek.dao.CommentRepo;
import com.bibliotek.domain.dto.comment.CommentView;
import com.bibliotek.domain.dto.comment.EditCommentRequest;
import com.bibliotek.domain.mapper.CommentEditMapper;
import com.bibliotek.domain.mapper.CommentViewMapper;
import com.bibliotek.domain.model.Book;
import com.bibliotek.domain.model.Comment;
import com.bibliotek.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private CommentViewMapper viewMapper;
    @Autowired
    private CommentEditMapper editMapper;


    @Override
    @Transactional
    public CommentView createComment(EditCommentRequest request) {
        Comment comment = editMapper.create(request);
        Book book = bookRepo.getById(request.getBookId());

        comment.setBook(book);
        book.getComments().add(comment);

        bookRepo.save(book);
        commentRepo.save(comment);

        log.info("Comment with content: {}... created.", request.getContent().substring(0, 10));
        return viewMapper.toCommentView(comment);
    }


    @Override
    @Transactional
    public CommentView updateComment(Long id, EditCommentRequest request) {
        Comment comment = commentRepo.getById(id);
        editMapper.update(request, comment);

        comment = commentRepo.save(comment);
        log.info("Comment with ID={} updated.", comment.getId());

        return viewMapper.toCommentView(comment);
    }

    @Override
    @Transactional
    public CommentView deleteComment(Long id) {
        Comment comment = commentRepo.getById(id);
        commentRepo.delete(comment);
        log.info("Comment with ID={} deleted.", comment.getId());
        return viewMapper.toCommentView(comment);
    }

    @Override
    public CommentView getCommentById(Long id) {
        return viewMapper.toCommentView(commentRepo.getById(id));
    }

//    @Override
//    public List<CommentView> getComments(Iterable<Long> ids) {
//        return viewMapper.toCommentView(commentRepo.findAllById(ids));
//    }

    @Override
    public List<CommentView> getBookComments(Long bookId) {
        Book book = bookRepo.getById(bookId);
        return viewMapper.toCommentView(book.getComments());
    }

    @Override
    public void updateBook(Comment comment) {
        Book book = bookRepo.getById(comment.getBook().getId());
        book.getComments().add(comment);
        bookRepo.save(book);
        log.info("Book with ID={} updated with new comment.", book.getId());
    }

    @Override
    public Long getCommentsCount() {
        return commentRepo.count();
    }
}
