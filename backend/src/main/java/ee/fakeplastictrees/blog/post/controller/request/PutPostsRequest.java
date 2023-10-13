package ee.fakeplastictrees.blog.post.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PutPostsRequest {
    @NotBlank
    String title;

    @NotBlank
    String summary;

    @NotBlank
    String text;

    @NotBlank
    String date;

    @NotNull
    Boolean visible;

    @NotEmpty
    Set<String> category;
}
