package com.example.lawson_prevost_tp1.domain;

import jakarta.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer idBook;
    public String title;

    @Column(unique = true)
    public String isbn;
    
    public int year;

    public enum Category{NOVEL, ESSAY, POETRY, OTHER};

    @Enumerated(EnumType.STRING)
    public Category category;

    @ManyToOne
    
    @JoinColumn(name = "author_id")
    private Author author;

// Getters / Setters

    public Integer getIdBook(){
        return idBook;
    }

    public void setIdBook (Integer idBook){
        this.idBook = idBook;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn (String isbn){
        this.isbn = isbn;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}