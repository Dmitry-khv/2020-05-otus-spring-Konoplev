package ru.otus.library.repository;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.library.domain.Book;

import java.util.List;



@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;


    @Override
    public List<Book> findAllByGenreId(String id) {
        return mongoTemplate.find(new Query(Criteria.where("genres.id").is(id)), Book.class);
    }

    @Override
    public List<Book> findAllByAuthorId(String id) {
        return mongoTemplate.find(new Query(Criteria.where("authors.id").is(id)), Book.class);
    }

    @Override
    public void removeAuthorById(String id) {
        Query query = Query.query(Criteria.where("$id").is(new ObjectId(id)));
        Update update = new Update().pull("authors", query);
        mongoTemplate.updateMulti(new Query(), update, Book.class);
    }

    @Override
    public void removeGenreById(String id) {
        Query query = Query.query(Criteria.where("$id").is(new ObjectId(id)));
        Update update = new Update().pull("genres", query);
        mongoTemplate.updateMulti(new Query(), update, Book.class);
    }
}
