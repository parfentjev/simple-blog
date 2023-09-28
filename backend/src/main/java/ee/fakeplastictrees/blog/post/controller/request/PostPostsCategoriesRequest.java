package ee.fakeplastictrees.blog.post.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostPostsCategoriesRequest {
    @NotBlank
    @Size(min = 1, max = 32)
    String name;
}
