package ee.fakeplastictrees.blog.post.repository;

import ee.fakeplastictrees.blog.post.model.Post;
import java.util.List;
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
          where p.visible = true
          """,
      countQuery = "select count(*) from posts p where p.visible = true",
      nativeQuery = true)
  Page<Post> findPublished(Pageable pageable);

  @Query(
      value =
          """
          select * from posts p
          where p.id = :postId
          and p.visible = true
          """,
      nativeQuery = true)
  Optional<Post> findPublishedById(String postId);

  @Query(
      value =
          """
          select * from posts p
          where p.id in (:postIds)
          and p.visible = true
          order by p.date desc
          """,
      nativeQuery = true)
  List<Post> findPublishedByIds(List<String> postIds);
}
