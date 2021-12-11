package com.bibliotek.web;


import com.bibliotek.domain.dto.author.AuthorView;
import com.bibliotek.domain.dto.book.BookView;
import com.bibliotek.domain.dto.comment.CommentView;
import com.bibliotek.domain.dto.comment.EditCommentRequest;
import com.bibliotek.web.data.AuthorTestDataFactory;
import com.bibliotek.web.data.BookTestDataFactory;
import com.bibliotek.web.data.CommentTestDataFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.bibliotek.util.JsonHelper.*;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("gesh.petrov@bs.io")
class TestCommentController {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final CommentTestDataFactory commentTestDataFactory;
    private final BookTestDataFactory bookTestDataFactory;
    private final AuthorTestDataFactory authorTestDataFactory;

    private BookView bookView;

    @Autowired
    TestCommentController(MockMvc mockMvc,
                          ObjectMapper objectMapper,
                          CommentTestDataFactory commentTestDataFactory,
                          BookTestDataFactory bookTestDataFactory,
                          AuthorTestDataFactory authorTestDataFactory) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.commentTestDataFactory = commentTestDataFactory;
        this.bookTestDataFactory = bookTestDataFactory;
        this.authorTestDataFactory = authorTestDataFactory;
    }

    @BeforeEach
    public void initViews() {
        AuthorView authorView = authorTestDataFactory.createAuthor("Test Author");
        this.bookView = bookTestDataFactory.createBook("Book Test", List.of(authorView.getId()));
    }

    @Test
    public void testCreatSuccsses() throws Exception {
        EditCommentRequest request = new EditCommentRequest();
        request.setContent("Test content");
        request.setBookId(bookView.getId());

        MvcResult createResult = this.mockMvc
                .perform(post("/api/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isOk())
                .andReturn();

        CommentView commentView = fromJson(objectMapper, createResult.getResponse().getContentAsString(), CommentView.class);
        assertNotNull(commentView.getId(), "Comment id must not be null!");
        assertEquals(request.getContent(), commentView.getContent(), "Comment content update isn't applied!");
    }

    @Test
    public void testCreateFail() throws Exception {
        EditCommentRequest request = new EditCommentRequest();

        this.mockMvc
                .perform(post("/api/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Method argument validation failed")));
    }

    @Test
    public void testEditSuccess() throws Exception {
        CommentView commentView = commentTestDataFactory.createComment("Test content", this.bookView.getId());

        EditCommentRequest request = new EditCommentRequest();
        request.setContent("Test new content");
        request.setBookId(bookView.getId());

        MvcResult createResult = this.mockMvc
                .perform(put(String.format("/api/comment/%s", commentView.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isOk())
                .andReturn();

        CommentView newCommentView = fromJson(objectMapper, createResult.getResponse().getContentAsString(), CommentView.class);
        assertNotNull(newCommentView.getId(), "Comment id must not be null!");
        assertEquals(request.getContent(), newCommentView.getContent(), "Comment content update isn't applied!");
    }

    @Test
    public void testEditFailBadRequest() throws Exception {
        CommentView commentView = commentTestDataFactory.createComment("Test content", this.bookView.getId());
        EditCommentRequest request = new EditCommentRequest();

        this.mockMvc
                .perform(put(String.format("/api/comment/%s", commentView.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Method argument validation failed")));
    }

    @Test
    public void testEditFailNotFound() throws Exception {
        EditCommentRequest request = new EditCommentRequest();
        request.setContent("Test content");
        request.setBookId(bookView.getId());

        this.mockMvc
                .perform(put(String.format("/api/comment/%s", 314))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity not found exception")));
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        CommentView commentView = commentTestDataFactory.createComment("Test content", this.bookView.getId());

        this.mockMvc
                .perform(delete(String.format("/api/comment/%s", commentView.getId())))
                .andExpect(status().isOk());

        this.mockMvc
                .perform(get(String.format("/api/comment/%s", commentView.getId())))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity not found exception")));
    }

    @Test
    public void testDeleteFailNotFound() throws Exception {
        this.mockMvc
                .perform(delete(String.format("/api/comment/%s", 314)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity not found exception")));
    }

    @Test
    public void testGetSuccess() throws Exception {
        CommentView commentView = commentTestDataFactory.createComment("Test content", this.bookView.getId());

        MvcResult createResult = this.mockMvc
                .perform(get(String.format("/api/comment/%s", commentView.getId())))
                .andExpect(status().isOk())
                .andReturn();

        CommentView newCommentView = fromJson(objectMapper, createResult.getResponse().getContentAsString(), CommentView.class);
        assertNotNull(newCommentView.getId(), "Comment id must not be null!");
        assertEquals(commentView.getId(), commentView.getId(), "Comment ids mismatch!");
    }

    @Test
    public void testGetNotFound() throws Exception {
        this.mockMvc
                .perform(get(String.format("/api/comment/%s", 314)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity not found exception")));
    }
}