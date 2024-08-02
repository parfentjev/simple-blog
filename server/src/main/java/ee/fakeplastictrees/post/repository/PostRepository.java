package ee.fakeplastictrees.post.repository;

import ee.fakeplastictrees.post.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, String> {
  Page<Post> findAll(Pageable pageable);

  Page<Post> findByVisible(Pageable pageable, boolean visible);
}
