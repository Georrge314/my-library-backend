package com.bibliotek.config;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse<T> {
    private Date timestamp;

    private String message;

    private List<T> details;
}
