package otus.amogilevskiy.spring.service;

import otus.amogilevskiy.spring.domain.jpa.Product;
import otus.amogilevskiy.spring.domain.mongo.MongoProduct;

public interface MongoProductProcessor {

    Product process(MongoProduct product);

}
