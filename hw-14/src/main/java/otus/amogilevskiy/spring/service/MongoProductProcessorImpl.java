package otus.amogilevskiy.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.jpa.Category;
import otus.amogilevskiy.spring.domain.jpa.Product;
import otus.amogilevskiy.spring.domain.mongo.MongoCategory;
import otus.amogilevskiy.spring.domain.mongo.MongoProduct;
import otus.amogilevskiy.spring.repository.jpa.CategoryRepository;

@Service
@RequiredArgsConstructor
public class MongoProductProcessorImpl implements MongoProductProcessor {

    private final CategoryRepository categoryRepository;

    @Override
    public Product process(MongoProduct mongoProduct) {
        return Product.builder()
                .title(mongoProduct.getTitle())
                .description(mongoProduct.getDescription())
                .basePrice(mongoProduct.getBasePrice())
                .currentPrice(mongoProduct.getCurrentPrice())
                .category(processCategory(mongoProduct.getCategory()))
                .build();
    }

    private Category processCategory(MongoCategory category) {
        return categoryRepository.findFirstByTitle(category.getTitle())
                .orElseThrow(() -> new RuntimeException(String.format("Category %s not found.", category.getTitle())));
    }

}
