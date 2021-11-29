package com.bibliotek.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @CreatedBy
    private Long creatorId;

    @NonNull
    @NotNull
    @Column(unique = true)
    @EqualsAndHashCode.Include
    private String title;

    @NonNull
    @NotNull
    @Column(columnDefinition = "MEDIUMTEXT")
    private String about;

    @NonNull
    @NotNull
    private String language;

    @NonNull
    @NotNull
    private String genre;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @NonNull
    @NotNull
    private Author author;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @NonNull
    @Column(name = "published_date")
    private LocalDate publishedDate;

    @Length(min = 5, max = 512)
    @Column(name = "image_url")
    private String imageUrl;

    private String isbn13;
    private String isbn10;
    private String publisher;
    private Double rating;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created = LocalDateTime.now();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modified = LocalDateTime.now();

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private Set<Comment> comments = new HashSet<>();
}


//    @JsonIgnore
//    @ManyToMany(mappedBy = "books")
//    @ToString.Exclude
//    private Set<User> users = new HashSet<>();
