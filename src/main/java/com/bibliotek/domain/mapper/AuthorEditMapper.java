package com.bibliotek.domain.mapper;

import com.bibliotek.domain.dto.author.EditAuthorRequest;
import com.bibliotek.domain.model.Author;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface AuthorEditMapper {
    Author create(EditAuthorRequest request);

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    void update(EditAuthorRequest request, @MappingTarget Author author);
}
