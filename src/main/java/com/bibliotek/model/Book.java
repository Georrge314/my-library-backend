package com.bibliotek.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Book {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Expose
    @NonNull
    @NotNull
    @Column(unique = true)
    private String title;

    @Expose
    @Length(min = 3, max = 40)
    @NotNull
    @NonNull
    private String author;

    @Expose
    @NonNull
    @NotNull
    private String genre;

    @Expose
    @JsonFormat(pattern = "yyyy:MM:dd")
    @NotNull
    @NonNull
    private LocalDate publishedDate;

    @Length(min = 5, max = 512)
    private String imageUrl;

    @Expose
    private String description;

    @Expose
    private Long rating;

    @Expose
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created = LocalDateTime.now();

    @Expose
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modified = LocalDateTime.now();


    @JsonIgnore
    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    @ToString.Exclude
    private Collection<User> users = new ArrayList<>();
}
