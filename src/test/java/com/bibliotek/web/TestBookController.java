package com.bibliotek.web;

import com.bibliotek.domain.dto.ListResponse;
import com.bibliotek.domain.dto.author.AuthorView;
import com.bibliotek.domain.dto.book.BookView;
import com.bibliotek.domain.dto.book.EditBookRequest;
import com.bibliotek.domain.dto.comment.CommentView;
import com.bibliotek.web.data.AuthorTestDataFactory;
import com.bibliotek.web.data.BookTestDataFactory;
import com.bibliotek.web.data.CommentTestDataFactory;
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

import java.time.LocalDate;
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
class TestBookController {

    private final BookTestDataFactory bookTestDataFactory;
    private final AuthorTestDataFactory authorTestDataFactory;
    private final CommentTestDataFactory commentTestDataFactory;
    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;


    @Autowired
    TestBookController(BookTestDataFactory bookTestDataFactory,
                       AuthorTestDataFactory authorTestDataFactory,
                       CommentTestDataFactory commentTestDataFactory,
                       ObjectMapper objectMapper,
                       MockMvc mockMvc) {
        this.bookTestDataFactory = bookTestDataFactory;
        this.authorTestDataFactory = authorTestDataFactory;
        this.commentTestDataFactory = commentTestDataFactory;
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
    }


    @Test
    public void testCreateSuccess() throws Exception {
        AuthorView firstAuthorView = authorTestDataFactory.createAuthor(
                "Ivan Vazov",
                "Bulgaria",
                "https://www.domain.bg/nothing",
                "Bulgarian author!",
                Set.of(CRIME.toString(), FANTASY.toString())
        );

        AuthorView secondAuthorView = authorTestDataFactory.createAuthor(
                "Hristo Botev",
                "Bulgaria",
                "https://www.domain.bg/nothing2",
                "Bulgarian author!",
                Set.of(TRAGEDY.toString(), HISTORY.toString())
        );

        EditBookRequest request = new EditBookRequest();
        request.setTitle("Bulgarian stories");
        request.setAbout("Amazing stories");
        request.setLanguage("Bulgarian");
        request.setGenres(Set.of(TRAGEDY.toString(), HISTORY.toString()));
        request.setPublishedDate(LocalDate.of(1886, 10, 10));
        request.setImageUrl("https://www.domain.bg/nothing/image");
        request.setIsbn10("12313-312321-3-1");
        request.setIsbn13("12313-312321-3-1");
        request.setPublisher("Kiril Petrkov");
        request.setAuthorIds(List.of(firstAuthorView.getId(), secondAuthorView.getId()));

        MvcResult createResult = this.mockMvc
                .perform(post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isOk())
                .andReturn();

        BookView bookView = fromJson(objectMapper, createResult.getResponse().getContentAsString(), BookView.class);
        assertNotNull(bookView.getId(), "Book id must not be null!");
        assertEquals(bookView.getTitle(), request.getTitle(), "Book title update isn't applied!");
    }

    @Test
    public void testCreateFail() throws Exception {
        EditBookRequest request = new EditBookRequest();
        request.setAbout("Invalid book request");

        this.mockMvc
                .perform(post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Method argument validation failed")));
    }

    @Test
    public void testEditSuccess() throws Exception {
        AuthorView authorView = authorTestDataFactory.createAuthor("Test Author");
        BookView bookView = bookTestDataFactory.createBook("Test Book", List.of(authorView.getId()));

        EditBookRequest request = new EditBookRequest();
        request.setTitle("Test New Book");
        request.setAbout("Awesome book!");

        MvcResult createResult = this.mockMvc
                .perform(put(String.format("/api/book/%s", bookView.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isOk())
                .andReturn();

        BookView newBookView = fromJson(objectMapper, createResult.getResponse().getContentAsString(), BookView.class);
        assertNotNull(newBookView.getId(), "Book id must not be null!");
        assertEquals(newBookView.getTitle(), request.getTitle(), "Book title update isn't applied!");
        assertEquals(newBookView.getAbout(), request.getAbout(), "Book about update isn't applied!");
    }

    @Test
    public void testEditFailBadRequest() throws Exception {
        AuthorView authorView = authorTestDataFactory.createAuthor("Test Author A");
        BookView bookView = bookTestDataFactory.createBook("Test Book A", List.of(authorView.getId()));

        EditBookRequest request = new EditBookRequest();
        request.setAbout("Invalid request");

        this.mockMvc
                .perform(put(String.format("/api/book/%s", bookView.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Method argument validation failed")));
    }

    @Test
    public void testEditFailNotFound() throws Exception {
        EditBookRequest updateRequest = new EditBookRequest();
        updateRequest.setTitle("Test Book");

        this.mockMvc
                .perform(put(String.format("/api/book/%s", 314))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, updateRequest)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity not found exception")));;
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        AuthorView authorView = authorTestDataFactory.createAuthor("Test Author");
        BookView bookView = bookTestDataFactory.createBook("Test Book", List.of(authorView.getId()));

        this.mockMvc
                .perform(delete(String.format("/api/book/%s", bookView.getId())))
                .andExpect(status().isOk());

        this.mockMvc
                .perform(get(String.format("/api/book/%s", bookView.getId())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteFailNotFound() throws Exception {
        this.mockMvc
                .perform(delete(String.format("/api/book/%s", 314)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity not found exception")));;
    }

    @Test
    public void testGetSuccess() throws Exception {
        AuthorView authorView = authorTestDataFactory.createAuthor("Test Author");
        BookView bookView = bookTestDataFactory.createBook("Test Book", List.of(authorView.getId()));

        MvcResult getResult = this.mockMvc
                .perform(get(String.format("/api/book/%s", bookView.getId())))
                .andExpect(status().isOk())
                .andReturn();

        BookView getBookView = fromJson(objectMapper, getResult.getResponse().getContentAsString(), BookView.class);

        assertEquals(bookView.getId(), getBookView.getId(), "Book ids must be equal!");
    }

    @Test
    public void testGetNotFound() throws Exception {
        this.mockMvc
                .perform(get(String.format("/api/book/%s", 314)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity not found exception")));
    }



    @Test
    public void testGetBookAuthorsSuccess() throws Exception {
        AuthorView authorView1 = authorTestDataFactory.createAuthor("Test Author A");
        AuthorView authorView2 = authorTestDataFactory.createAuthor("Test Author B");
        AuthorView authorView3 = authorTestDataFactory.createAuthor("Test Author C");

        BookView bookView = bookTestDataFactory.createBook("Test Book", List.of(
                authorView1.getId(),
                authorView2.getId(),
                authorView3.getId()));

        MvcResult getAuthorsResult = this.mockMvc
                .perform(get(String.format("/api/book/%s/author", bookView.getId())))
                .andExpect(status().isOk())
                .andReturn();

        ListResponse<AuthorView> authorViewList = fromJson(objectMapper,
                getAuthorsResult.getResponse().getContentAsString(),
                new TypeReference<>() {});
        authorViewList.getItems().sort(Comparator.comparing(AuthorView::getId));

        assertEquals(3, authorViewList.getItems().size(), "Book musth have 3 authors");
        assertEquals(authorView1.getFullName(), authorViewList.getItems().get(0).getFullName(), "Author fullname mismatch!");
        assertEquals(authorView2.getFullName(), authorViewList.getItems().get(1).getFullName(), "Author fullname mismatch!");
        assertEquals(authorView3.getFullName(), authorViewList.getItems().get(2).getFullName(), "Author fullname mismatch!");
    }

    @Test
    public void testGetBookCommentsSuccess() throws Exception {
        AuthorView authorView1 = authorTestDataFactory.createAuthor("Test Author A");
        BookView bookView = bookTestDataFactory.createBook("Test Book", List.of(authorView1.getId()));
        CommentView commentView1 = commentTestDataFactory.createComment("Coment content 1", bookView.getId());
        CommentView commentView2 = commentTestDataFactory.createComment("Coment content 2", bookView.getId());
        CommentView commentView3 = commentTestDataFactory.createComment("Coment content 3", bookView.getId());

        MvcResult getCommentResult = this.mockMvc
                .perform(get(String.format("/api/book/%s/comment", bookView.getId())))
                .andExpect(status().isOk())
                .andReturn();

        ListResponse<CommentView> commentViewList = fromJson(objectMapper,
                getCommentResult.getResponse().getContentAsString(),
                new TypeReference<>() {});
        commentViewList.getItems().sort(Comparator.comparing(CommentView::getId));

        assertEquals(3, commentViewList.getItems().size(), "Book must have 3 comments");
        assertEquals(commentView1.getContent(), commentViewList.getItems().get(0).getContent(), "Comment content mismatch!");
        assertEquals(commentView2.getContent(), commentViewList.getItems().get(1).getContent(), "Comment content mismatch!");
        assertEquals(commentView3.getContent(), commentViewList.getItems().get(2).getContent(), "Comment content mismatch!");
    }
}