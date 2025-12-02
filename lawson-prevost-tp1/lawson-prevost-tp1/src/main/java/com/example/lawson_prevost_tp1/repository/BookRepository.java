package com.example.lawson_prevost_tp1.repository;

import com.example.lawson_prevost_tp1.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {

    /**
     * Statistique : nombre de livres par catégorie
     */
    @Query("SELECT b.category, COUNT(b) FROM Book b GROUP BY b.category")
    List<Object[]> countBooksPerCategory();

    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndIdBookNot(String isbn, Integer idBook);
}
