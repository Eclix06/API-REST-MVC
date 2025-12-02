package com.example.lawson_prevost_tp1.repository;

import com.example.lawson_prevost_tp1.domain.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecifications {

    public static Specification<Book> hasTitle(String title) {
        return (root, query, cb) -> {
            if (title == null || title.isBlank()) return null;
            return cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
        };
    }

    public static Specification<Book> hasCategory(Book.Category category) {
        return (root, query, cb) -> {
            if (category == null) return null;
            return cb.equal(root.get("category"), category);
        };
    }

    public static Specification<Book> hasAuthorId(Integer authorId) {
        return (root, query, cb) -> {
            if (authorId == null) return null;
            return cb.equal(root.get("author").get("idAuthor"), authorId);
        };
    }

    public static Specification<Book> yearFrom(Integer yearFrom) {
        return (root, query, cb) -> {
            if (yearFrom == null) return null;
            return cb.greaterThanOrEqualTo(root.get("year"), yearFrom);
        };
    }

    public static Specification<Book> yearTo(Integer yearTo) {
        return (root, query, cb) -> {
            if (yearTo == null) return null;
            return cb.lessThanOrEqualTo(root.get("year"), yearTo);
        };
    }
}
