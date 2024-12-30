package ee.fakeplastictrees.blog.service.media.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends CrudRepository<Media, String> {
  Page<Media> findAll(Pageable pageable);
}
