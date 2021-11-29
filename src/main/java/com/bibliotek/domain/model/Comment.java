package com.bibliotek.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NonNull
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne()
    @JsonIgnore
    private User creator;

    @NonNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private Long creatorId;

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

    @ManyToOne()
    @JsonIgnore
    private Book book;

    @NonNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private Long bookId;
}
