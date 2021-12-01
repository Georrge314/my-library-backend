package com.bibliotek.init;

import com.bibliotek.domain.dto.user.CreateUserRequest;
import com.bibliotek.domain.model.Role;
import com.bibliotek.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class DatabaseInitiliazer implements CommandLineRunner {
    @Autowired
    private UserService userService;

    private final List<String> usernames = List.of(
            "gesh.petrov@bs.io",
            "mario.hulca@bs.io",
            "ivan.kiriazov@bs.io",
            "viktor.georgiev@bs.io"
    );

    private final List<String> emails = List.of(
            "georgiPetrov314@gmail.com",
            "marioiii@abv.bg",
            "ivan_kiriazov.@hotmail.com",
            "viktor_98@gmail.com"
    );

    private final List<String> fullNames = List.of(
            "Georgi Petrov",
            "Mario Hulca",
            "Ivan Kiriazov",
            "Viktor Antonov"
    );

    private final List<String> roles = List.of(
            Role.USER_ADMIN,
            Role.BOOK_ADMIN,
            Role.AUTHOR_ADMIN,
            Role.COMMENT_ADMIN
    );

    private final String password = "test112233";

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < usernames.size(); i++) {
            CreateUserRequest request = new CreateUserRequest();
            request.setUsername(usernames.get(i));
            request.setEmail(emails.get(i));
            request.setFullName(fullNames.get(i));
            request.setPassword(password);
            request.setRePassword(password);
            request.setAuthorities(Set.of(roles.get(i)));

            userService.upsert(request);
        }
    }
}
