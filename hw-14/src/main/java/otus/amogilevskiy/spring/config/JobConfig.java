package otus.amogilevskiy.spring.config;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobConfig {

    public static final String MIGRATE_MONGO_TO_JPA_JOB = "migrateMongoToJpaJob";

    private final JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job migrateMongoToJpaJob(
            Step categoryMigrationStep,
            Step productMigrationStep
    ) {
        return jobBuilderFactory.get(MIGRATE_MONGO_TO_JPA_JOB)
                .start(categoryMigrationStep)
                .next(productMigrationStep)
                .build();
    }
}