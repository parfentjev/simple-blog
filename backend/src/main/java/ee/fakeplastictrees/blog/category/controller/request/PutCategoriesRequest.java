package ee.fakeplastictrees.blog.category.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PutCategoriesRequest {
    @NotBlank
    String name;
}
