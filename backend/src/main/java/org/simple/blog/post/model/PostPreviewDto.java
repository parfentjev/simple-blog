package org.simple.blog.post.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostPreviewDto {
    String id;

    String title;

    String summary;

    String date;
}
