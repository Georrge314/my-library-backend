package com.bibliotek.config;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
public class ErrorResponse<T> {
    private LocalDateTime timestamp = LocalDateTime.now();

    @NonNull
    private String message;

    @NonNull
    private List<T> details;
}
