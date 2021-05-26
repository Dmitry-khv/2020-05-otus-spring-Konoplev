package otus.amogilevskiy.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.jpa.Category;
import otus.amogilevskiy.spring.domain.mongo.MongoCategory;
import otus.amogilevskiy.spring.repository.jpa.CategoryRepository;

@Service
@RequiredArgsConstructor
public class MongoCategoryProcessorImpl implements MongoCategoryProcessor {

    private final CategoryRepository categoryRepository;

    @Override
    public Category process(MongoCategory mongoCategory) {
        return categoryRepository.save(Category.builder()
                .title(mongoCategory.getTitle())
                .build()
        );
    }
}
