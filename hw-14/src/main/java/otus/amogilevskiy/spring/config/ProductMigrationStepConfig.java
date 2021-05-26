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
import otus.amogilevskiy.spring.domain.jpa.Product;
import otus.amogilevskiy.spring.domain.mongo.MongoProduct;
import otus.amogilevskiy.spring.service.MongoProductProcessor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ProductMigrationStepConfig {

    private static final int PRODUCT_MIGRATION_CHUNK_SIZE = 2;
    private static final String PRODUCT_MIGRATION_STEP_NAME = "PRODUCT_MIGRATION_STEP";
    private static final String PRODUCT_READER_NAME = "PRODUCT_READER";

    private final MongoTemplate mongoTemplate;
    private final StepBuilderFactory stepBuilderFactory;
    private final MongoProductProcessor mongoProductProcessor;
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public MongoItemReader<MongoProduct> productReader() {
        return new MongoItemReaderBuilder<MongoProduct>()
                .name(PRODUCT_READER_NAME)
                .template(mongoTemplate)
                .jsonQuery("{}")
                .targetType(MongoProduct.class)
                .sorts(Map.of())
                .build();
    }

    @Bean
    public JpaItemWriter<Product> productWriter() {
        return new JpaItemWriterBuilder<Product>()
                .entityManagerFactory(entityManager.getEntityManagerFactory())
                .usePersist(true)
                .build();
    }

    @Bean
    public ItemProcessor<MongoProduct, Product> productProcessor() {
        return mongoProductProcessor::process;
    }

    @Bean
    public Step productMigrationStep(
            MongoItemReader<MongoProduct> productReader,
            ItemProcessor<MongoProduct, Product> productProcessor,
            JpaItemWriter<Product> productWriter
    ) {
        return stepBuilderFactory.get(PRODUCT_MIGRATION_STEP_NAME)
                .<MongoProduct, Product>chunk(PRODUCT_MIGRATION_CHUNK_SIZE)
                .reader(productReader)
                .processor(productProcessor)
                .writer(productWriter)
                .build();
    }

}
