package otus.amogilevskiy.spring.db;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import otus.amogilevskiy.spring.domain.mongo.MongoCategory;
import otus.amogilevskiy.spring.domain.mongo.MongoProduct;
import otus.amogilevskiy.spring.repository.mongo.CategoryMongoRepository;
import otus.amogilevskiy.spring.repository.mongo.ProductMongoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@ChangeLog(order = "001")
public class MongoDatabaseChangelog {

    @ChangeSet(order = "001", id = "categories", author = "konoplev")
    public void categories(CategoryMongoRepository categoryRepository) {
        categoryRepository.save(new MongoCategory("snacks"));
        categoryRepository.save(new MongoCategory("drinks"));
        categoryRepository.save(new MongoCategory("clothes"));
    }

    @ChangeSet(order = "002", id = "products_drinks", author = "konoplev")
    public void drinksProducts(CategoryMongoRepository categoryRepository, ProductMongoRepository productRepository) {
        var category = categoryRepository.findFirstByTitle("drinks")
                .orElseThrow();

        var productTitles = List.of(
                "Best Juice",
                "Best Water",
                "Best Milk"
        );

        generateProducts(productTitles, category).forEach(productRepository::save);
    }

    @ChangeSet(order = "003", id = "products_snacks", author = "konoplev")
    public void snacksProducts(CategoryMongoRepository categoryRepository, ProductMongoRepository productRepository) {
        var category = categoryRepository.findFirstByTitle("snacks")
                .orElseThrow();

        var productTitles = List.of(
                "Best Chips",
                "Best Nuts",
                "Best Cracker"
        );

        generateProducts(productTitles, category).forEach(productRepository::save);
    }

    private List<MongoProduct> generateProducts(List<String> titles, MongoCategory category) {
        var random = new Random();
        return titles.stream().map(title -> {
            var basePrice = random.nextInt(100000);
            var currentPrice = Math.round(basePrice * 0.9);
            return MongoProduct.builder()
                    .title(title)
                    .description(String.format("Description for %s", title))
                    .basePrice(BigDecimal.valueOf(basePrice, 2))
                    .currentPrice(BigDecimal.valueOf(currentPrice, 2))
                    .category(category)
                    .build();
        }).collect(Collectors.toList());
    }

}