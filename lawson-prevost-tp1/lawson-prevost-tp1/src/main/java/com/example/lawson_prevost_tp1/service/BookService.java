package com.example.lawson_prevost_tp1.service;

import com.example.lawson_prevost_tp1.domain.Author;
import com.example.lawson_prevost_tp1.domain.Book;
import com.example.lawson_prevost_tp1.dto.BookDto;
import com.example.lawson_prevost_tp1.dto.BookUpdateDto;
import com.example.lawson_prevost_tp1.exception.BadRequestException;
import com.example.lawson_prevost_tp1.exception.NotFoundException;
import com.example.lawson_prevost_tp1.repository.AuthorRepository;
import com.example.lawson_prevost_tp1.repository.BookRepository;
import com.example.lawson_prevost_tp1.repository.BookSpecifications;

import java.util.Objects;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    // GET /books (avec filtres + pagination + tri)
    public Page<Book> getAll(String title,
                             Integer authorId,
                             String category,
                             Integer yearFrom,
                             Integer yearTo,
                             int page,
                             int size,
                             String sort) {

        if (sort == null || sort.isBlank()) {
            sort = "idBook,asc";  // ou "id,asc" selon ton entity
        }
        
        // Parse du tri : "year,desc" / "id,asc" etc.
        String[] parts = sort.split(",");
        String sortProperty = parts[0];
        Sort.Direction direction = Sort.Direction.ASC;
        if (parts.length > 1 && parts[1].equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortProperty));

        // Conversion String -> enum Category
        Book.Category categoryEnum = null;
        if (category != null && !category.isBlank()) {
            try {
                categoryEnum = Book.Category.valueOf(category.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Invalid category: " + category);
            }
        }

        Specification<Book> spec = Specification.allOf(
                BookSpecifications.hasTitle(title),
                BookSpecifications.hasAuthorId(authorId),
                BookSpecifications.hasCategory(categoryEnum),
                BookSpecifications.yearFrom(yearFrom),
                BookSpecifications.yearTo(yearTo)
        );

        return bookRepository.findAll(spec, pageable);
    }

    // GET /books/{id}
    public Book getById(int id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with id " + id));
    }

    // POST /books
    public Book create(BookDto dto) {

        // isbn doit être unique
        if (bookRepository.existsByIsbn(dto.getIsbn())) {
            throw new BadRequestException("ISBN must be unique");
        }

        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new NotFoundException("Author not found with id " + dto.getAuthorId()));

        Book book = new Book();
        applyCreateDtoToBook(dto, book, author);

        return bookRepository.save(book);
    }

    // PUT /books/{id}
    public Book update(Integer id, BookUpdateDto dto) {

        Book book = getById(id); // 404 si inexistant

        // Vérifier ISBN unique si modifié
        if (dto.getIsbn() != null &&
                bookRepository.existsByIsbnAndIdBookNot(dto.getIsbn(), id)) {
            throw new BadRequestException("ISBN must be unique");
        }

        Author author = null;
        Integer newAuthorId = dto.getAuthorId(); // rend l'intention plus claire pour l'IDE
        if (newAuthorId != null) {
            author = authorRepository.findById(newAuthorId)
                    .orElseThrow(() -> new NotFoundException("Author not found with id " + newAuthorId));
        }

        applyUpdateDtoToBook(dto, book, author);

        book = Objects.requireNonNull(book, "book must not be null");
        
        return bookRepository.save(book);
    }

// DELETE /books/{id}
public void delete(int id) {
    // on garde le 404 explicite :
    if (!bookRepository.existsById(id)) {
        throw new NotFoundException("Book not found with id " + id);
    }

    bookRepository.deleteById(id);
}

    // ----- MAPPING DTO -> ENTITY -----

    private void applyCreateDtoToBook(BookDto dto, Book book, Author author) {
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setYear(dto.getYear());

        try {
            Book.Category cat = Book.Category.valueOf(dto.getCategory().toUpperCase());
            book.setCategory(cat);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid category: " + dto.getCategory());
        }

        book.setAuthor(author);
    }

    private void applyUpdateDtoToBook(BookUpdateDto dto, Book book, Author author) {

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
            try {
                Book.Category cat = Book.Category.valueOf(dto.getCategory().toUpperCase());
                book.setCategory(cat);
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Invalid category: " + dto.getCategory());
            }
        }

        if (author != null) {
            book.setAuthor(author);
        }
    }
}
