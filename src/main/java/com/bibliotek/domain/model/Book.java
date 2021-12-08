package com.bibliotek.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

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
@EqualsAndHashCode(exclude = {"comments", "authors"})
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
    @Enumerated(EnumType.STRING)
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

    @Nullable
    @CreatedBy
    @ManyToOne
    private User creator;
    @Nullable
    @LastModifiedBy
    @ManyToOne
    private User modifier;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();
}
