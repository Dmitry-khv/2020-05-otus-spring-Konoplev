package otus.amogilevskiy.spring.job;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import otus.amogilevskiy.spring.domain.jpa.Category;
import otus.amogilevskiy.spring.repository.jpa.CategoryRepository;
import otus.amogilevskiy.spring.repository.jpa.ProductRepository;
import otus.amogilevskiy.spring.repository.mongo.CategoryMongoRepository;
import otus.amogilevskiy.spring.repository.mongo.ProductMongoRepository;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static otus.amogilevskiy.spring.config.JobConfig.MIGRATE_MONGO_TO_JPA_JOB;

@SpringBootTest
@SpringBatchTest
public class MigrateMongoToJpaJobTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private ProductMongoRepository productMongoRepository;

    @Autowired
    private CategoryMongoRepository categoryMongoRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void beforeEach() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void shouldReturnCompleteStatus() throws Exception {
        var job = jobLauncherTestUtils.getJob();
        var execution = jobLauncherTestUtils.launchJob(new JobParameters());

        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(MIGRATE_MONGO_TO_JPA_JOB);
        assertThat(execution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
    }

    @Test
    void shouldMigrateAllProducts() throws Exception {
        jobLauncherTestUtils.launchJob(new JobParameters());
        var expectedProducts = productMongoRepository.findAll();
        var actualProducts = productRepository.findAll();

        for (var i = 0; i < actualProducts.size(); i++) {
            var expectedProduct = expectedProducts.get(i);
            var actualProduct = actualProducts.get(i);

            assertThat(actualProduct).usingRecursiveComparison()
                    .ignoringFields("id", "category")
                    .isEqualTo(expectedProduct);

            assertThat(actualProduct.getCategory())
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(expectedProduct.getCategory());
        }
    }

    @Test
    void shouldMigrateAllCategories() throws Exception {
        jobLauncherTestUtils.launchJob(new JobParameters());
        var initialCategories = categoryMongoRepository.findAll();
        var expectedCategories = initialCategories.stream()
                .map(category -> Category.builder()
                        .title(category.getTitle())
                        .build()
                ).collect(Collectors.toList());

        var actualCategories = categoryRepository.findAll();

        assertThat(actualCategories).usingElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrderElementsOf(expectedCategories);
    }

}
