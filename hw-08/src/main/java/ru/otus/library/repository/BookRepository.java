package ru.otus.library.repository;


import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findBooksByTitle(String title);

    List<Book> findBooksByAuthors(Author author);

}
