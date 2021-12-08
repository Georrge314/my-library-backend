package com.bibliotek.web;

import com.bibliotek.domain.dto.ListResponse;
import com.bibliotek.domain.dto.author.AuthorView;
import com.bibliotek.domain.dto.book.BookView;
import com.bibliotek.domain.dto.book.EditBookRequest;
import com.bibliotek.domain.dto.comment.CommentView;
import com.bibliotek.domain.model.Book;
import com.bibliotek.domain.model.Role;
import com.bibliotek.service.AuthorService;
import com.bibliotek.service.BookService;
import com.bibliotek.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/book")
@CrossOrigin("http://localhost:3000")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private CommentService commentService;

    @GetMapping("{id}")
    public BookView getBooks(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @RolesAllowed(Role.BOOK_ADMIN)
    @DeleteMapping("{id}")
    public BookView deleteBook(@PathVariable Long id) {
        return bookService.deleteBook(id);
    }

    @RolesAllowed(Role.BOOK_ADMIN)
    @PostMapping
    public BookView createBook(@RequestBody @Valid EditBookRequest request) {
        return bookService.createBook(request);
    }

    @RolesAllowed(Role.BOOK_ADMIN)
    @PutMapping("{id}")
    public BookView editBook(@PathVariable Long id, @RequestBody @Valid EditBookRequest request) {
        return bookService.updateBook(id, request);
    }

    @GetMapping("{id}/author")
    public ListResponse<AuthorView> getAuthors(@PathVariable Long id) {
        return new ListResponse<>(authorService.getBookAuthors(id));
    }

    @GetMapping("{id}/comment")
    public ListResponse<CommentView> getComments(@PathVariable Long id) {
        return new ListResponse<>(commentService.getBookComments(id));
    }

    //TODO: impl search book that return list of books..
}
