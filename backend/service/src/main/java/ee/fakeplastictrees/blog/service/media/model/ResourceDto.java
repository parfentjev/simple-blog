package ee.fakeplastictrees.blog.service.media.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResourceDto {
  String contentType;

  Resource resource;
}
