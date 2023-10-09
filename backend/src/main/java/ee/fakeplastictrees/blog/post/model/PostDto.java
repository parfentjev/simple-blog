package ee.fakeplastictrees.blog.post.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDto {
    String id;

    String title;

    String summary;

    String text;

    String date;

    Boolean visible;

    Set<String> category;
}
