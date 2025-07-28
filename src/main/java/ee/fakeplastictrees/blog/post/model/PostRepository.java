package ee.fakeplastictrees.blog.post.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<Post, String> {
  Page<Post> findAll(Pageable pageable);

  Page<Post> findByVisible(Pageable pageable, boolean visible);

  @Query(value = """
    select * from posts
    where id = :id
    and visible = 1
    """, nativeQuery = true)
  Optional<Post> findVisibleById(String id);
}
