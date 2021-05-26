package ru.otus.library.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.domain.Book;

import java.util.List;


public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {

    List<Book> findBooksByTitle(String title);

}
