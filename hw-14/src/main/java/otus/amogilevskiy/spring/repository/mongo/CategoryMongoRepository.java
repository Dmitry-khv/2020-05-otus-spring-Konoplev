package otus.amogilevskiy.spring.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import otus.amogilevskiy.spring.domain.mongo.MongoCategory;

import java.util.Optional;

public interface CategoryMongoRepository extends MongoRepository<MongoCategory, String> {

    Optional<MongoCategory> findFirstByTitle(String title);

}
