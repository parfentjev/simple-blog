package ee.fakeplastictrees.blog.core.model.factory;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageRequestFactory {
  public static PageRequest withPage(int page, int pageSize, String sortBy) {
    return PageRequest.of(page - 1, pageSize).withSort(Sort.by(sortBy).descending());
  }
}
