package com.example.lawson_prevost_tp1.service;

import com.example.lawson_prevost_tp1.domain.Author;
import com.example.lawson_prevost_tp1.dto.AuthorDto;
import com.example.lawson_prevost_tp1.exception.NotFoundException;
import com.example.lawson_prevost_tp1.repository.AuthorRepository;
import com.example.lawson_prevost_tp1.exception.BadRequestException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // GET /authors
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    // GET /authors/{id}
    public Author getById(Integer id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found with id " + id));
    }

    // POST /authors
    public Author create(AuthorDto dto) {
        Author author = new Author();
        applyDtoToAuthor(dto, author);
        return authorRepository.save(author);
    }

    // PUT /authors/{id}
    public Author update(Integer id, AuthorDto dto) {
        Author author = getById(id); // lève 404 si inexistant
        applyDtoToAuthor(dto, author);
        return authorRepository.save(author);
    }

    // DELETE /authors/{id}
    public void delete(Integer id) {
        Author author = getById(id); // lève 404 si inexistant
        
    if (author.getBooks() != null && !author.getBooks().isEmpty()) {
        throw new BadRequestException(
            "Impossible de supprimer un auteur qui a encore des livres"
        );
    }

        authorRepository.delete(author);
    }

    // Méthode privée de mapping DTO -> Entity
    private void applyDtoToAuthor(AuthorDto dto, Author author) {
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        // seulement si tu as ce champ dans ton DTO et ton entity :
        author.setBirthDate(dto.getBirthDate());
    }
}
