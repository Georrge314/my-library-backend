package com.bibliotek.init;

import com.bibliotek.model.Book;
import com.bibliotek.model.User;
import com.bibliotek.service.BookService;
import com.bibliotek.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    private static final Set<Book> SAMPLE_BOOKS = Set.of(
            new Book(
                    "Podigoto",
                    "Ivan Vazov",
                    "roman",
                    LocalDate.of(1912, 2, 24)
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
                    LocalDate.of(2005, 12, 11)
            )
    );

    private static final Set<User> SAMPLE_USERS = Set.of(
            new User(
                    "Georgi",
                    "Petrov",
                    "admin@abv.bg",
                    "admin98admin",
                    User.ROLE_ADMIN),
            new User("Elena",
                    "Markova",
                    "flywithlove@gmail.com",
                    "elena9813",
                    User.ROLE_USER)
    );


    @Override
    public void run(String... args) throws Exception {
        if (bookService.getBooksCount() == 0) {
            SAMPLE_BOOKS.forEach(book -> {
                book.setCreated(LocalDateTime.now());
                book.setModified(LocalDateTime.now());
                bookService.createBook(book);
            });
            log.info("Created Books: " + bookService.getBooks());
        }

        if (userService.getUsersCount() == 0) {
            SAMPLE_USERS.forEach(user -> {
                user.setCreated(new Date());
                user.setModified(new Date());
                user.setBooks(SAMPLE_BOOKS);
                userService.createUser(user);
            });
            log.info("Created Users: " + userService.getUsers());
        }
    }
}
