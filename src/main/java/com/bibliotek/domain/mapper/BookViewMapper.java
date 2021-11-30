package com.bibliotek.domain.mapper;

import com.bibliotek.domain.dto.book.BookView;
import com.bibliotek.domain.model.Book;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class BookViewMapper {
    @Autowired
    private UserViewMapper userViewMapper;

    public abstract BookView toBookView(Book book);

    public abstract List<BookView> toBookView(List<Book> books);

    @AfterMapping
    protected void after(Book book, @MappingTarget BookView bookView) {
        bookView.setCreator(userViewMapper.toUserViewById(book.getCreatorId()));
    }
}
