package com.bibliotek.web;

import com.bibliotek.domain.dto.ListResponse;
import com.bibliotek.domain.dto.author.AuthorView;
import com.bibliotek.domain.dto.author.EditAuthorRequest;
import com.bibliotek.domain.dto.book.BookView;
import com.bibliotek.domain.model.Role;
import com.bibliotek.service.AuthorService;
import com.bibliotek.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("api/author")
@CrossOrigin("http://localhost:3000")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @RolesAllowed(Role.AUTHOR_ADMIN)
    @PostMapping
    public AuthorView createAuthor(@RequestBody @Valid EditAuthorRequest request) {
        return authorService.createAuthor(request);
    }

    @RolesAllowed(Role.AUTHOR_ADMIN)
    @PutMapping("{id}")
    public AuthorView editAuthor(@PathVariable Long id, @Valid @RequestBody EditAuthorRequest request) {
        return authorService.updateAuthor(id, request);
    }

    @RolesAllowed(Role.AUTHOR_ADMIN)
    @DeleteMapping("{id}")
    public AuthorView deleteAuthor(@PathVariable Long id) {
        return authorService.deleteAuthor(id);
    }

    @GetMapping("Id")
    public AuthorView getAuthor(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping("{id}/book")
    public ListResponse<BookView> getBooks(@PathVariable Long id) {
        return new ListResponse<>(bookService.getAuthorBooks(id));
    }

    //TODO impl search method
}
