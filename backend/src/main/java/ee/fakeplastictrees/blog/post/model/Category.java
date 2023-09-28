package ee.fakeplastictrees.blog.post.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("categories")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    String id;

    String name;
}
