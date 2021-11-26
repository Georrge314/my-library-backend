package com.bibliotek.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authors")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Length(min = 5, max = 50)
    @NonNull
    @NotNull
    private String fullName;

    @Length(min = 3, max = 50)
    @NonNull
    @NotNull
    private String country;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NonNull
    @NotNull
    private LocalDate born;

    private String imageUrl;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate died;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Book> books = new HashSet<>();
}
