package ru.otus.library.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.domain.Book;

import java.util.List;
import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    @EntityGraph(attributePaths = {"author", "genre", "comment"})
    Optional<Book>findById(long id);

    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();

    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findBooksByTitle(String name);

    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findBooksByAuthor_Name(String name);

    @EntityGraph(attributePaths = "comment")
    void deleteById(long id);
}
