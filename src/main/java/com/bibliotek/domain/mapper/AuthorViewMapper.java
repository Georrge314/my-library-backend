package com.bibliotek.domain.mapper;

import com.bibliotek.domain.dto.author.AuthorView;
import com.bibliotek.domain.model.Author;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AuthorViewMapper {
    @Autowired
    private UserViewMapper userViewMapper;

    public abstract AuthorView toAuthorView(Author author);

    public abstract List<AuthorView> toAuthorView(List<Author> authors);

    @AfterMapping
    public void after(Author author, @MappingTarget AuthorView authorView) {
        authorView.setCreator(userViewMapper.toUserViewById(author.getCreatorId()));
    }
}
