package otus.amogilevskiy.spring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import otus.amogilevskiy.spring.domain.jpa.Category;
import otus.amogilevskiy.spring.domain.mongo.MongoCategory;
import otus.amogilevskiy.spring.service.MongoCategoryProcessor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CategoryMigrationStepConfig {

    private static final int CATEGORY_MIGRATION_CHUNK_SIZE = 10;
    private static final String CATEGORY_MIGRATION_STEP_NAME = "CATEGORY_MIGRATION_STEP";
    private static final String CATEGORY_READER_NAME = "CATEGORY_READER";

    private final MongoTemplate mongoTemplate;
    private final StepBuilderFactory stepBuilderFactory;
    private final MongoCategoryProcessor mongoCategoryProcessor;
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public MongoItemReader<MongoCategory> categoryReader() {
        return new MongoItemReaderBuilder<MongoCategory>()
                .name(CATEGORY_READER_NAME)
                .template(mongoTemplate)
                .jsonQuery("{}")
                .targetType(MongoCategory.class)
                .sorts(Map.of())
                .build();
    }

    @Bean
    public JpaItemWriter<Category> categoryWriter() {
        return new JpaItemWriterBuilder<Category>()
                .entityManagerFactory(entityManager.getEntityManagerFactory())
                .usePersist(true)
                .build();
    }

    @Bean
    public ItemProcessor<MongoCategory, Category> categoryProcessor() {
        return mongoCategoryProcessor::process;
    }

    @Bean
    public Step categoryMigrationStep(
            MongoItemReader<MongoCategory> categoryReader,
            ItemProcessor<MongoCategory, Category> categoryProcessor,
            JpaItemWriter<Category> categoryWriter
    ) {
        return stepBuilderFactory.get(CATEGORY_MIGRATION_STEP_NAME)
                .<MongoCategory, Category>chunk(CATEGORY_MIGRATION_CHUNK_SIZE)
                .reader(categoryReader)
                .processor(categoryProcessor)
                .writer(categoryWriter)
                .build();
    }
}
