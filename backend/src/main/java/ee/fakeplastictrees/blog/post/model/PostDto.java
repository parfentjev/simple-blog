package ee.fakeplastictrees.blog.post.model;

import ee.fakeplastictrees.blog.category.model.CategoryDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

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

    List<CategoryDto> categories;
}
