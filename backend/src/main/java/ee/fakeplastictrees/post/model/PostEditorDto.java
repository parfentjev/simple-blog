package ee.fakeplastictrees.post.model;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostEditorDto {
  String id;

  @NotNull
  String title;

  @NotNull
  String summary;

  @NotNull
  String text;

  Instant date;

  @NotNull
  Boolean visible;
}
