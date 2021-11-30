package com.bibliotek.domain.mapper;


import com.bibliotek.domain.dto.comment.EditCommentRequest;
import com.bibliotek.domain.model.Comment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface CommentEditMapper {
    Comment create(EditCommentRequest request);

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    void update(EditCommentRequest request, @MappingTarget Comment comment);
}
