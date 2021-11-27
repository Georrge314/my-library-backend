package com.bibliotek.init;

import com.bibliotek.model.Author;
import com.bibliotek.model.Book;
import com.bibliotek.model.Comment;
import com.bibliotek.model.User;
import com.bibliotek.service.BookService;
import com.bibliotek.service.CommentService;
import com.bibliotek.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private CommentService commentService;

    private static final Set<Book> SAMPLE_BOOKS = Set.of(
            new Book("Title",
                    new Author(
                            "Ivan Markov",
                            "Bulgaria",
                            LocalDate.of(1960,12,12)
                    ),
                    "genre",
                    LocalDate.of(1925, 10, 10),
                    "some description",
                    2L,
                    1L
            ),

            new Book(
                    "New Title",
                    new Author(
                            "Steven King",
                            "USA",
                            LocalDate.of(1947,12,12)
                    ),
                    "new genre",
                    LocalDate.of(2010, 5, 8),
                    "new description book",
                    10L,
                    0L
            ),

            new Book(
                    "Another Book",
                    new Author(
                            "Dave Markov",
                            "Bulgaria",
                            LocalDate.of(1987,12,12)
                    ),
                    "another genre",
                    LocalDate.of(2015, 10, 12),
                    "new description book 2",
                    10L,
                    10L

            )
    );

    private static final Set<User> SAMPLE_USERS = Set.of(
            new User(
                    "Georgi",
                    "Petrov",
                    "admin@abv.bg",
                    "112233",
                    User.ROLE_ADMIN),

            new User("Elena",
                    "Markova",
                    "elena98@gmail.com",
                    "112233",
                    User.ROLE_ADMIN),

            new User("Ivan",
                    "Petrov",
                    "ivanPv@gmail.com",
                    "112233",
                    User.ROLE_USER),

            new User("Pesho",
                    "Vasilev",
                    "peshoVasilev@gmail.com",
                    "112233",
                    User.ROLE_USER)
    );


    @Override
    public void run(String... args) throws Exception {
        if (bookService.getBooksCount() == 0) {
            SAMPLE_BOOKS.forEach(book -> {
                bookService.createBook(book);
            });
            log.info("Created Books: " + bookService.getBooks());
        }

        if (userService.getUsersCount() == 0) {
            SAMPLE_USERS.forEach(user -> {
                user.setBooks(SAMPLE_BOOKS);
                userService.createUser(user);
            });
            log.info("Created Users: " + userService.getUsers());
        }
    }
}
