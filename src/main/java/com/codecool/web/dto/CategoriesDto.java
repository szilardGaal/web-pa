package com.codecool.web.dto;

import java.util.List;

public final class CategoriesDto {

    private final List<Integer> ids;
    private final List<String> names;

    public CategoriesDto(List<Integer> ids, List<String> names) {
        this.ids = ids;
        this.names = names;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public List<String> getNames() {
        return names;
    }
}
