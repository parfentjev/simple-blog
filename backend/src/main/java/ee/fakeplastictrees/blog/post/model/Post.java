package ee.fakeplastictrees.blog.post.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("posts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {
    @Id
    String id;

    String title;

    String summary;

    String text;

    String date;

    Boolean visible;
}

