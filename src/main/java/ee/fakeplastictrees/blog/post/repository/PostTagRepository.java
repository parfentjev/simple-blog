package ee.fakeplastictrees.blog.post.repository;

import ee.fakeplastictrees.blog.post.model.PostTag;
import ee.fakeplastictrees.blog.post.model.PostTag.PostTagId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTagRepository extends CrudRepository<PostTag, PostTagId> {
  // todo: native query with JOIN
}
