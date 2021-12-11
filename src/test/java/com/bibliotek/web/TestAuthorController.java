package com.bibliotek.web;

import com.bibliotek.domain.dto.ListResponse;
import com.bibliotek.domain.dto.author.AuthorView;
import com.bibliotek.domain.dto.author.EditAuthorRequest;
import com.bibliotek.domain.dto.book.BookView;
import com.bibliotek.web.data.AuthorTestDataFactory;
import com.bibliotek.web.data.BookTestDataFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static com.bibliotek.domain.model.Genre.*;
import static com.bibliotek.util.JsonHelper.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("gesh.petrov@bs.io")
class TestAuthorController {
    private final AuthorTestDataFactory authorTestDataFactory;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final BookTestDataFactory bookTestDataFactory;

    @Autowired
    TestAuthorController(AuthorTestDataFactory authorTestDataFactory, MockMvc mockMvc, ObjectMapper objectMapper, BookTestDataFactory bookTestDataFactory) {
        this.authorTestDataFactory = authorTestDataFactory;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.bookTestDataFactory = bookTestDataFactory;
    }

    @Test
    public void testCreateSuccess() throws Exception {
        EditAuthorRequest request = new EditAuthorRequest();
        request.setFullName("Author Test");
        request.setAbout("Greate Author");
        request.setNationality("Bulgaria");
        request.setImageUrl("https://www.domain.com/image/test");
        request.setGenres(Set.of(CRIME.toString(), ROMANCE.toString()));

        MvcResult createResult = this.mockMvc
                .perform(post("/api/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isOk())
                .andReturn();

        AuthorView authorView = fromJson(objectMapper, createResult.getResponse().getContentAsString(), AuthorView.class);
        assertNotNull(authorView.getId(), "Author id must not be null!");
        assertEquals(authorView.getFullName(), request.getFullName(), "Author name update isn't applied!");
    }

    @Test
    public void testCreateFail() throws Exception {
        EditAuthorRequest request = new EditAuthorRequest();

        this.mockMvc
                .perform(post("/api/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Method argument validation failed")));
    }

    @Test
    public void testEditSuccess() throws Exception {
        AuthorView authorView = authorTestDataFactory.createAuthor("Test Author");

        EditAuthorRequest request = new EditAuthorRequest();
        request.setFullName("Test Author New");
        request.setAbout("Greate Author");
        request.setNationality("Bulgaria");
        request.setImageUrl("https://www.domain.com/image/test");

        MvcResult createResult = this.mockMvc
                .perform(put(String.format("/api/author/%s", authorView.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isOk())
                .andReturn();

        AuthorView newAuthorView = fromJson(objectMapper, createResult.getResponse().getContentAsString(), AuthorView.class);
        assertNotNull(newAuthorView.getId(), "Author id must not be null!");
        assertNotNull(newAuthorView.getAbout(), "Author about update isn't applied!");
        assertEquals(newAuthorView.getFullName(), request.getFullName(), "Author fullname update isn't applied!");
    }

    @Test
    public void testEditFailBadRequest() throws Exception {
        AuthorView authorView = authorTestDataFactory.createAuthor("Test Author");

        EditAuthorRequest updateRequest = new EditAuthorRequest();

        this.mockMvc
                .perform(put(String.format("/api/author/%s", authorView.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, updateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Method argument validation failed")));
    }

    @Test
    public void testEditFailNotFound() throws Exception {
        EditAuthorRequest updateRequest = new EditAuthorRequest();
        updateRequest.setFullName("Test Author");

        this.mockMvc
                .perform(put(String.format("/api/author/%s", 314))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, updateRequest)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity not found exception")));
    }

    @Test
    public void testDeleteSuccsses() throws Exception {
        AuthorView authorView = authorTestDataFactory.createAuthor("Author Test");

        this.mockMvc
                .perform(delete(String.format("/api/author/%s", authorView.getId())))
                .andExpect(status().isOk());
        this.mockMvc
                .perform(get(String.format("/api/author/%s", authorView.getId())))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity not found exception")));
        ;
    }

    @Test
    public void testDeleteFailNotFound() throws Exception {
        this.mockMvc
                .perform(delete(String.format("/api/author/%s", 314)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity not found exception")));
    }

    @Test
    public void testGetSuccess() throws Exception {
        AuthorView authorView = authorTestDataFactory.createAuthor("Test Author");

        MvcResult getResult = this.mockMvc
                .perform(get(String.format("/api/author/%s", authorView.getId())))
                .andExpect(status().isOk())
                .andReturn();

        AuthorView newAuthorView = fromJson(objectMapper, getResult.getResponse().getContentAsString(), AuthorView.class);
        assertEquals(authorView.getId(), newAuthorView.getId(), "Author ids must be equal!");
    }

    @Test
    public void testGetNotFound() throws Exception {
        this.mockMvc
                .perform(get(String.format("/api/author/%s", 314)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity not found exception")));
    }

    @Test
    public void testGetAuthorBooksSuccess() throws Exception {
        AuthorView authorView1 = authorTestDataFactory.createAuthor("Author Test A");
        AuthorView authorView2 = authorTestDataFactory.createAuthor("Author Test B");

        BookView bookView1 = bookTestDataFactory.createBook(
                "Book Test A",
                List.of(authorView1.getId(), authorView2.getId())
        );

        BookView bookView2 = bookTestDataFactory.createBook(
                "Book Test B",
                List.of(authorView1.getId(), authorView2.getId())
        );

        BookView bookView3 = bookTestDataFactory.createBook(
                "Book Test C",
                List.of(authorView1.getId(), authorView2.getId())
        );

        MvcResult createResult = this.mockMvc
                .perform(get(String.format("/api/author/%s/book", authorView1.getId())))
                .andExpect(status().isOk())
                .andReturn();

        ListResponse<BookView> bookViewList = fromJson(objectMapper,
                createResult.getResponse().getContentAsString(), new TypeReference<>() {
                });
        bookViewList.getItems().sort(Comparator.comparing(BookView::getId));

        assertEquals(3, bookViewList.getItems().size(), "Books must be 3!");
        assertEquals(bookView1.getTitle(), bookViewList.getItems().get(0).getTitle(), "Book title mismath!");
        assertEquals(bookView2.getTitle(), bookViewList.getItems().get(1).getTitle(), "Book title mismath!");
        assertEquals(bookView3.getTitle(), bookViewList.getItems().get(2).getTitle(), "Book title mismath!");
    }

}