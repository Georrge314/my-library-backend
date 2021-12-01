package com.bibliotek.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest<T> {
    @Valid
    @NotNull
    private Page page;

    @Valid
    @NotNull
    private T query;

    public SearchRequest(T query) {
        this.query = query;
        this.page = new Page();
    }
}
