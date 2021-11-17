package com.bibliotek.dao;

import com.bibliotek.model.Book;
import com.bibliotek.model.User;
import com.bibliotek.service.BookService;
import com.bibliotek.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    private static final List<Book> SAMPLE_BOOKS = List.of(
            new Book(
                    "Podigoto",
                    "Ivan Vazov",
                    "roman",
                    LocalDate.of(1912,2,24)
            ),
            new Book(
                    "It",
                    "Steven King",
                    "horror",
                    LocalDate.of(1980, 10, 14)
            ),
            new Book(
                    "Short story about everything",
                    "Nqkoi si",
                    "qka",
                    LocalDate.of(2005,12,11)
            )
    );

    private static final List<User> SAMPLE_USERS = List.of(
            new User(
                    "Georgi",
                    "Petrov",
                    "admin@abv.bg",
                    "admin",
                    User.ROLE_ADMIN),
            new User("Elena",
                    "Markova",
                    "flywithlove@gmail.com",
                    "elena98",
                    User.ROLE_USER)
    );


    @Override
    public void run(String... args) throws Exception {
        SAMPLE_USERS.forEach(user -> {
            user.setBooks(SAMPLE_BOOKS);
            userService.createUser(user);
        });
        log.info("Created Users: " + userService.getUsers());
        log.info("Created Books: " + bookService.getBooks());
    }
}
