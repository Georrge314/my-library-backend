package com.bibliotek.domain.mapper;

import com.bibliotek.domain.dto.user.CreateUserRequest;
import com.bibliotek.domain.dto.user.UpdateUserRequest;
import com.bibliotek.domain.model.Role;
import com.bibliotek.domain.model.User;
import org.mapstruct.*;

import java.util.stream.Collectors;

import static org.mapstruct.NullValueCheckStrategy.*;
import static org.mapstruct.NullValuePropertyMappingStrategy.*;

@Mapper(componentModel = "spring")
public abstract class UserEditMapper {
    @Mapping(target = "authorities", ignore = true)
    public abstract User create(CreateUserRequest request);

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "authorities", ignore = true)
    public abstract void update(UpdateUserRequest request, @MappingTarget User user);

    @AfterMapping
    protected void afterCreate(CreateUserRequest request, @MappingTarget User user) {
        if (request.getAuthorities() != null) {
            user.setAuthorities(request.getAuthorities().stream().map(Role::new).collect(Collectors.toSet()));
        }
    }

    @AfterMapping
    protected void afterUpdate(UpdateUserRequest request, @MappingTarget User user) {
        if (request.getAuthorities() != null) {
            user.setAuthorities(request.getAuthorities().stream().map(Role::new).collect(Collectors.toSet()));
        }
    }
}
