package com.bibliotek.web;

import com.bibliotek.exception.InvalidEntityException;
import com.bibliotek.model.Book;
import com.bibliotek.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/books")
@CrossOrigin("http://localhost:3000")
@Slf4j
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public Collection<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("{id}")
    public Book getBooks(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @DeleteMapping("{id}")
    public Book deleteBook(@PathVariable Long id) {
        return bookService.deleteBook(id);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book created = bookService.createBook(book);
        URI location = MvcUriComponentsBuilder.fromMethodName(BookController.class, "createBook", Book.class)
                .pathSegment("{id}").buildAndExpand(created.getId()).toUri();
        log.info("Book created: {}", book.getTitle());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        if (book.getId() != id) {
            throw new InvalidEntityException(
                    String.format("Book ID=%s from path is different from Entity ID=%s", id, book.getId()));
        }
        Book updated = bookService.updateBook(book);
        log.info("Book updated: {}", updated);
        return ResponseEntity.ok(updated);
    }
}
