package otus.amogilevskiy.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import otus.amogilevskiy.spring.repository.mongo.CategoryMongoRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class CategoryMongoRepositoryTest {

    @Autowired
    private CategoryMongoRepository categoryMongoRepository;

    @Test
    void shouldReturnAllCategories() {
        var actualCategories = categoryMongoRepository.findAll();

        assertThat(actualCategories.size()).isEqualTo(3);
    }

}
