package ee.fakeplastictrees.core.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageDto<T> {
  Integer page;

  Integer totalPages;

  List<T> items;
}
