package ee.fakeplastictrees.blog.post.repository;

import ee.fakeplastictrees.blog.post.model.Tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<Tag, String> {
  @Query(
      value =
          """
          select * from tags
          where lower(name) = lower(:name)
          limit 1
          """,
      nativeQuery = true)
  Optional<Tag> findByName(String name);
}
