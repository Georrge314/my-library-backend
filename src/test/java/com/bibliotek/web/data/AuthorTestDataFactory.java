package com.bibliotek.web.data;

import com.bibliotek.domain.dto.author.AuthorView;
import com.bibliotek.domain.dto.author.EditAuthorRequest;
import com.bibliotek.service.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Service
public class AuthorTestDataFactory {
    @Autowired
    private AuthorService authorService;

    public AuthorView createAuthor(String fullName,
                                   String nationality,
                                   String imageUrl,
                                   String about,
                                   Set<String> genres) {
        EditAuthorRequest request = new EditAuthorRequest();
        request.setFullName(fullName);
        request.setNationality(nationality);
        request.setImageUrl(imageUrl);
        request.setAbout(about);
        request.setGenres(genres);

        AuthorView authorView = authorService.createAuthor(request);
        assertNotNull(authorView.getId(), "Author id must not be null!");
        assertEquals(request.getFullName(), authorView.getFullName(), "Author update isn't applied!");

        return authorView;
    }

    public AuthorView createAuthor(String fullName,
                                   String nationality,
                                   String imageUrl,
                                   String about) {
        return createAuthor(fullName, nationality, imageUrl, about, null);
    }

    public AuthorView createAuthor(String fullName,
                                   String nationality,
                                   String imageUrl) {
        return createAuthor(fullName, nationality, imageUrl, null, null);
    }

    public AuthorView createAuthor(String fullName,
                                   String nationality) {
        return createAuthor(fullName, nationality, null, null, null);
    }

    public AuthorView createAuthor(String fullName) {
        return createAuthor(fullName, null, null, null, null);
    }

    public void deleteAuthor(Long id) {
        authorService.deleteAuthor(id);
    }
}
