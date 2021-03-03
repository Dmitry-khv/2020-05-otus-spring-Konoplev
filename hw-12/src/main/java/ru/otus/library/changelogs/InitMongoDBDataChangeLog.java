package ru.otus.library.changelogs;


import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.GenreRepository;
import ru.otus.library.repository.UserRepository;
import ru.otus.library.rest.dto.UserDTO;

import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Author tolkien;
    private Author bloch;
    private Genre fantasy;
    private Genre study;
    private Genre adventure;
    private UserDTO user;

    @ChangeSet(order = "000", id = "dropDB", author = "konoplev", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthor", author = "konoplev", runAlways = true)
    public void initAuthors(AuthorRepository authorRepository) {
        tolkien = authorRepository.save(new Author("1a", "Tolkien"));
        bloch = authorRepository.save(new Author("Bloch"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "konoplev", runAlways = true)
    public void initGenres(GenreRepository genreRepository) {
        fantasy = genreRepository.save(new Genre("1g", "Fantasy"));
        study = genreRepository.save(new Genre("2g", "Study"));
        adventure = genreRepository.save(new Genre("3g", "Adventure"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "konoplev", runAlways = true)
    public void initBooks(BookRepository repository) {
        Book book1 = new Book();
        book1.setId("1");
        book1.setTitle("Lord of the Ring");
        book1.getAuthors().add(tolkien);
        book1.addGenre(adventure, fantasy);
        book1.getComments().addAll(List.of(new Comment("amazing"), new Comment("great"), new Comment("very nice")));
        repository.save(book1);

        Book book2 = new Book();
        book2.setId("2");
        book2.setTitle("Effective Java");
        book2.addAuthor(bloch);
        book2.addGenre(study);
        book2.getComments().addAll(List.of(new Comment("not bad"), new Comment("not enough good")));
        repository.save(book2);

        Book book3 = new Book();
        book3.setId("3");
        book3.setTitle("Hobbit");
        book3.getAuthors().add(tolkien);
        book3.addGenre(adventure, fantasy);
        book3.getComments().addAll(List.of(new Comment("amazing"), new Comment("great"), new Comment("very nice")));
        repository.save(book3);
    }

    @ChangeSet(order = "004", id = "initUsers", author = "konoplev", runAlways = true)
    public void initUsers(UserRepository repository) {
        user = new UserDTO("1", "admin", "password", 3);
        repository.save(user);
        user = new UserDTO("2", "user", "password", 2);
        repository.save(user);
        user = new UserDTO("3", "guest", "password", 1);
        repository.save(user);
        user = new UserDTO("4", "blocked", "password", 0);
        repository.save(user);
    }
}
