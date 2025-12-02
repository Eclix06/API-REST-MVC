package com.example.lawson_prevost_tp1.mapper;

import com.example.lawson_prevost_tp1.domain.Author;
import com.example.lawson_prevost_tp1.dto.AuthorDto;

public final class AuthorMapper {

    private AuthorMapper() {
        // util class, pas d'instance
    }

    public static Author fromDto(AuthorDto dto) {
        Author author = new Author();
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        author.setBirthDate(dto.getBirthDate());
        return author;
    }

    public static void updateEntity(AuthorDto dto, Author author) {
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        author.setBirthDate(dto.getBirthDate());
    }

    public static AuthorDto toDto(Author author) {
        AuthorDto dto = new AuthorDto();
        dto.setFirstName(author.getFirstName());
        dto.setLastName(author.getLastName());
        dto.setBirthDate(author.getBirthDate());
        return dto;
    }
}
