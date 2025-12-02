package com.example.lawson_prevost_tp1.repository;

import com.example.lawson_prevost_tp1.domain.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    /**
     * Statistique : auteurs ayant le plus de livres
     */
    @Query("""
            SELECT a.idAuthor, a.firstName, a.lastName, COUNT(b)
            FROM Author a
            LEFT JOIN a.books b
            GROUP BY a.idAuthor, a.firstName, a.lastName
            ORDER BY COUNT(b) DESC
            """)
    List<Object[]> findTopAuthors(Pageable pageable);
}
