package otus.amogilevskiy.spring.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@Builder
@Document(collection = "categories")
@AllArgsConstructor
@NoArgsConstructor
public class MongoCategory {

    @Id
    private String id;

    private String title;

    public MongoCategory(String title) {
        this.title = title;
    }
}
