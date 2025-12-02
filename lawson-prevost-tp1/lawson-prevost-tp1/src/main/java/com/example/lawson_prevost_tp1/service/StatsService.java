package com.example.lawson_prevost_tp1.service;

import com.example.lawson_prevost_tp1.repository.AuthorRepository;
import com.example.lawson_prevost_tp1.repository.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatsService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public StatsService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    /**
     * GET /stats/books-per-category
     * → retourne le nombre de livres par catégorie
     */
    public List<Map<String, Object>> getBooksPerCategory() {

        List<Object[]> result = bookRepository.countBooksPerCategory();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : result) {
            Map<String, Object> item = new HashMap<>();
            item.put("category", row[0]);
            item.put("count", row[1]);
            response.add(item);
        }

        return response;
    }

    /**
     * GET /stats/top-authors?limit=3
     * → retourne les auteurs ayant publié le plus de livres
     */
    public List<Map<String, Object>> getTopAuthors(int limit) {

        List<Object[]> result = authorRepository.findTopAuthors(PageRequest.of(0, limit));
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : result) {
            Map<String, Object> item = new HashMap<>();
            item.put("authorId", row[0]);
            item.put("firstName", row[1]);
            item.put("lastName", row[2]);
            item.put("bookCount", row[3]);
            response.add(item);
        }

        return response;
    }
}
