package ee.fakeplastictrees.blog.service.core.model;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PageRequestFactory {
  public PageRequest withPage(int page, int pageSize, String sortBy) {
    return PageRequest.of(page - 1, pageSize).withSort(Sort.by(sortBy).descending());
  }
}
