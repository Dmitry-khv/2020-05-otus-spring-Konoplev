package otus.amogilevskiy.spring.domain.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal basePrice;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal currentPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
