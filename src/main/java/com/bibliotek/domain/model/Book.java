package com.bibliotek.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String title;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String about;
    @Column
    private String language;
    @Column
    @ElementCollection
    private Set<Genre> genres = new HashSet<>();
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "published_date")
    private LocalDate publishedDate;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "isbn_13")
    private String isbn13;
    @Column(name = "isbn_10")
    private String isbn10;
    @Column
    private String publisher;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @CreatedBy
    @ManyToOne
    private User creator;
    @LastModifiedBy
    @ManyToOne
    private User modifier;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private Set<Author> authors = new HashSet<>();

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();
}
