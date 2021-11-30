package com.bibliotek.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authors")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Author implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    @Column
    private String nationality;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String about;
    @Column
    @Enumerated(value = EnumType.STRING)
    private Set<Genre> genres = new HashSet<>();

    @CreatedBy
    @Column(name = "creator_id")
    private Long creatorId;
    @LastModifiedBy
    @Column(name = "modifier_id")
    private Long modifierId;
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created = LocalDateTime.now();
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modified = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books = new HashSet<>();
}
