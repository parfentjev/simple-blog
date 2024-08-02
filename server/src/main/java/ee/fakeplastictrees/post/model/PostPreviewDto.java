package ee.fakeplastictrees.post.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostPreviewDto {
  String id;

  String title;

  String summary;

  Instant date;

  Boolean visible;
}
