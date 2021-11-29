package com.bibliotek.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@JsonIgnoreProperties({"authorities", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled"})
public class User implements UserDetails {
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NonNull
    @Length(min = 5, max = 40)
    @Column(unique = true)
    @NotNull
    @EqualsAndHashCode.Include
    private String username;

    @NonNull
    @Length(min = 4, max = 100)
    @Column(nullable = false)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NonNull
    @Length(min = 4, max = 100)
    @Column(nullable = false)
    @NotNull
    @Email
    private String email;

    @NonNull
    @Length(min = 2, max = 40)
    private String firstName;

    @NonNull
    @Length(min = 2, max = 40)
    private String lastName;

    @NonNull
    @NotNull
    private Set<Role> authorities = new HashSet<>();

    @Length(min = 8, max = 512)
    @Column(name = "image_url")
    private String imageUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created = LocalDateTime.now();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modified = LocalDateTime.now();

    private boolean active = true;

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}

//@ManyToMany()
//    @JoinTable(
//            name = "users_books",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "book_id"))
//    @ToString.Exclude
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    private Set<Book> books = new HashSet<>();
