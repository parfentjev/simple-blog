package ee.fakeplastictrees.blog.post.repository;

import ee.fakeplastictrees.blog.post.model.Post;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, String> {
  Page<Post> findAll(Pageable pageable);

  Page<Post> findByVisible(Pageable pageable, boolean visible);

  @Query(
      value =
          """
            select * from posts
            where id = :id
            and visible = 1
            """,
      nativeQuery = true)
  Optional<Post> findVisibleById(String id);
}
