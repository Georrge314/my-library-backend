package com.bibliotek.domain.mapper;

import com.bibliotek.domain.dto.book.EditBookRequest;
import com.bibliotek.domain.model.Book;
import org.mapstruct.*;

import static org.mapstruct.NullValueCheckStrategy.*;
import static org.mapstruct.NullValuePropertyMappingStrategy.*;

@Mapper(componentModel = "spring")
public interface BookEditMapper {

    Book create(EditBookRequest request);

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    void update(EditBookRequest request, @MappingTarget Book book);
}
