package ru.otus.library.changelogs;


import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.BookRepository;

import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    @ChangeSet(order = "000", id = "dropDB", author = "konoplev", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initBooks", author = "konoplev", runAlways = true)
    public void initBooks(BookRepository repository) {
        Author tolkien = new Author("Tolkien");
        Genre fantasy = new Genre("Fantasy");
        Book book1 = new Book();
        book1.setId("1");
        book1.setTitle("Lord of the Ring");
        book1.getAuthors().add(tolkien);
        book1.getGenres().add(fantasy);
        book1.getComments().addAll(List.of(new Comment("amazing"), new Comment("great"), new Comment("very nice")));
        repository.save(book1);

        Author bloch = new Author("Bloch");
        Genre genre = new Genre("Study");
        Book book2 = new Book();
        book2.setId("2");
        book2.setTitle("Effective Java");
        book2.addAuthor(bloch);
        book2.addGenre(genre);
        book2.getComments().addAll(List.of(new Comment("not bad"), new Comment("not enough good")));
        repository.save(book2);
    }
}
