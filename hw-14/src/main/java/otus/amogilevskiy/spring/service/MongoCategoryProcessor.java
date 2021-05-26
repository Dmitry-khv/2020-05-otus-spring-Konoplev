package otus.amogilevskiy.spring.service;

import otus.amogilevskiy.spring.domain.jpa.Category;
import otus.amogilevskiy.spring.domain.mongo.MongoCategory;

public interface MongoCategoryProcessor {

    Category process(MongoCategory mongoCategory);

}
