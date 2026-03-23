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

  @Query(
      value =
          """
          select * from posts p
          where p.visible = 1
          """,
      countQuery = "select count(*) from posts p where p.visible = 1",
      nativeQuery = true)
  Page<Post> findPublished(Pageable pageable);

  @Query(
      value =
          """
          select * from posts p
          where p.id = :id
          and p.visible = 1
          """,
      nativeQuery = true)
  Optional<Post> findPublishedById(String id);
}
