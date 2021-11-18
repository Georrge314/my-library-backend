package com.bibliotek.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
@JsonIgnoreProperties({"hibernateLazyInitializer"})
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
    @EqualsAndHashCode.Include
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
    private Set<User> users = new HashSet<>();

    public void addUser(User user) {
        this.users.add(user);
        user.getBooks().add(this);
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.getBooks().remove(this);
    }
}
