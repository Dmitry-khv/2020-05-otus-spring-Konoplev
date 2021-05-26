package otus.amogilevskiy.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import otus.amogilevskiy.spring.repository.mongo.ProductMongoRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class ProductMongoRepositoryTest {

    @Autowired
    private ProductMongoRepository productMongoRepository;

    @Test
    void shouldReturnAllCategories() {
        var actualCategories = productMongoRepository.findAll();

        assertThat(actualCategories.size()).isEqualTo(6);
    }

}
