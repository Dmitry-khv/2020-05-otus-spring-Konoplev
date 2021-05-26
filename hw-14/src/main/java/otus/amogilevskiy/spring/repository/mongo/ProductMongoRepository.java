package otus.amogilevskiy.spring.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import otus.amogilevskiy.spring.domain.mongo.MongoProduct;

public interface ProductMongoRepository extends MongoRepository<MongoProduct, String> {
}
