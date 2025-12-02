package com.example.lawson_prevost_tp1.dto;

import jakarta.validation.constraints.*;

public class BookDto {

    @NotBlank(message = "Title is required")
    private String title;

    @Pattern(regexp = "^[0-9]{10,13}$", message = "ISBN must be between 10 and 13 digits")
    private String isbn;

    @Min(value = 1450, message = "Year must be after 1450")
    @Max(value = 2100, message = "Year must be reasonable")
    private int year;

    @NotBlank(message = "Category is required")
    private String category; // NOVEL / ESSAY / POETRY / OTHER

    @NotNull(message = "AuthorId is required")
    private Integer authorId;

    // Getters / Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }
}
