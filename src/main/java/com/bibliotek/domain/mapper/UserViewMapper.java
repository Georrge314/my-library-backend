package com.bibliotek.domain.mapper;

import com.bibliotek.dao.UserRepo;
import com.bibliotek.domain.dto.user.UserView;
import com.bibliotek.domain.model.User;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class UserViewMapper {
    @Autowired
    private UserRepo userRepo;

    public abstract UserView toUserView(User user);

    public abstract Set<UserView> toUserView(Set<User> users);

    public UserView toUserViewById(Long id) {
        if (id == null) {
            return null;
        }
        return toUserView(userRepo.getById(id));
    }

}
