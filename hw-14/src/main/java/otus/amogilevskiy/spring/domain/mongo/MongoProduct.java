package otus.amogilevskiy.spring.domain.mongo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Builder
@Document(collection = "products")
public class MongoProduct {

    @Id
    private String id;

    private String title;

    private String description;

    @Column(precision = 11, scale = 2)
    private BigDecimal basePrice;

    @Column(precision = 11, scale = 2)
    private BigDecimal currentPrice;

    private MongoCategory category;

}
