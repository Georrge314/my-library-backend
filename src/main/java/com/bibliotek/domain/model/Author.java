package com.bibliotek.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authors")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Genre> genres = new HashSet<>();

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column
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

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private Set<Book> books = new HashSet<>();
}
