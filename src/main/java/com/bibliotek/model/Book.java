package com.bibliotek.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.transaction.annotation.Transactional;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NonNull
    @NotNull
    @Column(unique = true)
    @EqualsAndHashCode.Include
    private String title;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @NonNull
    @NotNull
    private Author author;

    @NonNull
    @NotNull
    private String genre;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @NonNull
    private LocalDate publishedDate;

    @Length(min = 5, max = 512)
    private String imageUrl;

    @NonNull
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String description;

    @NonNull
    @NotNull
    private Long likes;

    @NonNull
    @NotNull
    private Long dislikes;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created = LocalDateTime.now();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modified = LocalDateTime.now();


    @JsonIgnore
    @ManyToMany(mappedBy = "books")
    @ToString.Exclude
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private Set<Comment> comments = new HashSet<>();

    public void addUser(User user) {
        this.users.add(user);
        user.getBooks().add(this);
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.getBooks().remove(this);
    }
}
