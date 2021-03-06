package com.bibliotek.domain.mapper;

import com.bibliotek.domain.dto.author.AuthorView;
import com.bibliotek.domain.model.Author;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class AuthorViewMapper {
    @Autowired
    private UserViewMapper userViewMapper;

    public abstract AuthorView toAuthorView(Author author);

    public abstract List<AuthorView> toAuthorView(Set<Author> authors);

    public abstract List<AuthorView> toAuthorView(List<Author> authors);

    @AfterMapping
    public void after(Author author, @MappingTarget AuthorView authorView) {
        try {
            authorView.setCreator(userViewMapper.toUserViewById(author.getCreator().getId()));
        } catch (NoSuchElementException | NullPointerException exception) {
            author.setCreator(null);
        }
    }
}
