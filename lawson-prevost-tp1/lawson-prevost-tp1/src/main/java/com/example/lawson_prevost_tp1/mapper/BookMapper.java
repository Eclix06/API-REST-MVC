package com.example.lawson_prevost_tp1.mapper;

import com.example.lawson_prevost_tp1.domain.Author;
import com.example.lawson_prevost_tp1.domain.Book;
import com.example.lawson_prevost_tp1.dto.BookDto;
import com.example.lawson_prevost_tp1.dto.BookUpdateDto;
import com.example.lawson_prevost_tp1.exception.BadRequestException;

public final class BookMapper {

    private BookMapper() {
        // util class, pas d'instance
    }

    public static Book fromCreateDto(BookDto dto, Author author) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setYear(dto.getYear());
        book.setCategory(parseCategory(dto.getCategory()));
        book.setAuthor(author);
        return book;
    }

    public static void updateEntity(BookUpdateDto dto, Book book, Author author) {

        if (dto.getTitle() != null) {
            book.setTitle(dto.getTitle());
        }

        if (dto.getIsbn() != null) {
            book.setIsbn(dto.getIsbn());
        }

        if (dto.getYear() != null) {
            book.setYear(dto.getYear());
        }

        if (dto.getCategory() != null) {
            book.setCategory(parseCategory(dto.getCategory()));
        }

        if (author != null) {
            book.setAuthor(author);
        }
    }

    private static Book.Category parseCategory(String category) {
        if (category == null || category.isBlank()) {
            throw new BadRequestException("Category is required");
        }
        try {
            return Book.Category.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid category: " + category);
        }
    }
}
