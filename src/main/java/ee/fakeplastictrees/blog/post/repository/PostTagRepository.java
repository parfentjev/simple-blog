package ee.fakeplastictrees.blog.post.repository;

import ee.fakeplastictrees.blog.post.model.PostTag;
import ee.fakeplastictrees.blog.post.model.PostTag.PostTagId;
import ee.fakeplastictrees.blog.post.model.PostTagDto;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTagRepository extends CrudRepository<PostTag, PostTagId> {
  @Query(
      value =
          """
          select pt.post_id, t.id, t.name, t.slug from post_tags pt
          join tags t on t.id = pt.tag_id
          where pt.post_id = :postId
          """,
      nativeQuery = true)
  List<PostTagDto> findByPostId(String postId);

  @Query(
      value =
          """
          select pt.post_id, t.id, t.name, t.slug from post_tags pt
          join tags t on t.id = pt.tag_id
          where pt.post_id in (:postIds)
          """,
      nativeQuery = true)
  List<PostTagDto> findByPostIds(List<String> postIds);
}
