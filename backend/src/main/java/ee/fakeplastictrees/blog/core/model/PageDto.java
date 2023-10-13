package ee.fakeplastictrees.blog.core.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageDto<T> {
    Integer page;

    Integer totalPages;

    List<T> items;
}
