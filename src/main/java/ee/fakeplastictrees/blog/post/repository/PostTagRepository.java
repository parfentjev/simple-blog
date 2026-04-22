package ee.fakeplastictrees.blog.post.repository;

import ee.fakeplastictrees.blog.post.model.PostTag;
import ee.fakeplastictrees.blog.post.model.PostTag.PostTagId;
import ee.fakeplastictrees.blog.post.model.PostTagDto;
import ee.fakeplastictrees.blog.post.model.Tag;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

  @Query(
      value =
          """
          select pt.post_id from post_tags pt
          join tags t on t.id = pt.tag_id
          join posts p on p.id = pt.post_id
          where t.slug = :slug
          and p.visible = 1
          """,
      countQuery =
          """
          select count(*) from post_tags pt
          join tags t on t.id = pt.tag_id
          join posts p on p.id = pt.post_id
          where t.slug = :slug
          and p.visible = 1
          """,
      nativeQuery = true)
  Page<String> findPostIdsBySlug(String slug, Pageable pageable);

  @Query(
      value =
          """
          insert into post_tags (
            post_id, tag_id
          )
          values(
            :postId, :tagId
          )
          """,
      nativeQuery = true)
  @Modifying
  @Transactional
  void attach(String postId, String tagId);

  @Query(
      value =
          """
          delete from post_tags pt
          where pt.post_id = :postId
          and pt.tag_id = :tagId
          """,
      nativeQuery = true)
  @Modifying
  @Transactional
  void detach(String postId, String tagId);

  @Query(
      value =
          """
          select t.id, t.name, t.slug, count(*) as score
          from post_tags pt1
          join post_tags pt2 on pt1.post_id = pt2.post_id
          join tags t on t.id = pt2.tag_id
          where pt1.tag_id in (:attachedTagIds)
          and pt2.tag_id not in (:attachedTagIds)
          group by t.id
          order by score desc
          limit 10;
          """,
      nativeQuery = true)
  List<Tag> findRecommendedTags(List<String> attachedTagIds);
}
