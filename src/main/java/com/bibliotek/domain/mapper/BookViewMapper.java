package com.bibliotek.domain.mapper;

import com.bibliotek.domain.dto.book.BookView;
import com.bibliotek.domain.model.Author;
import com.bibliotek.domain.model.Book;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Mapper(componentModel = "spring")
public abstract class BookViewMapper {
    @Autowired
    private UserViewMapper userViewMapper;
    @Autowired
    private AuthorViewMapper authorViewMapper;
    @Autowired
    private CommentViewMapper commentViewMapper;

    public abstract BookView toBookView(Book book);

    public abstract List<BookView> toBookView(Set<Book> books);

    @AfterMapping
    protected void after(Book book, @MappingTarget BookView bookView) {

        try {
            bookView.setCreator(userViewMapper.toUserViewById(book.getCreator().getId()));
        } catch (NoSuchElementException | NullPointerException exception) {
            bookView.setCreator(null);
        }

        if (!CollectionUtils.isEmpty(book.getAuthors())) {
            bookView.setAuthors(new HashSet<>(authorViewMapper.toAuthorView(book.getAuthors())));
        }
        if (!CollectionUtils.isEmpty(book.getComments())) {
            bookView.setComments(new HashSet<>(commentViewMapper.toCommentView(book.getComments())));
        }
    }
}
