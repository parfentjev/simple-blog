package ee.fakeplastictrees.blog.post.service;

import static ee.fakeplastictrees.blog.core.model.factory.PageRequestFactory.pageable;
import static java.util.stream.Collectors.groupingBy;

import ee.fakeplastictrees.blog.post.model.PostTagDto;
import ee.fakeplastictrees.blog.post.model.TagDto;
import ee.fakeplastictrees.blog.post.model.mapper.TagMapper;
import ee.fakeplastictrees.blog.post.repository.PostTagRepository;
import ee.fakeplastictrees.blog.post.repository.TagRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TagService {
  private final TagRepository tagRepository;
  private final PostTagRepository postTagRepository;

  @Value("${posts.page.size:20}")
  private Integer pageSize;

  public TagService(TagRepository tagRepository, PostTagRepository postTagRepository) {
    this.tagRepository = tagRepository;
    this.postTagRepository = postTagRepository;
  }

  public List<PostTagDto> getTagsByPostId(String postId) {
    return postTagRepository.findByPostId(postId);
  }

  public Map<String, List<PostTagDto>> getTagsByPostIds(List<String> postIds) {
    return postTagRepository.findByPostIds(postIds).stream()
        .collect(groupingBy(PostTagDto::postId));
  }

  public Page<String> getPostIdsBySlug(String slug, Integer pageNumber) {
    var pageable = pageable(pageNumber, pageSize, "p.date");

    return postTagRepository.findPostIdsBySlug(slug, pageable);
  }

  public Optional<TagDto> getTagByName(String name) {
    return tagRepository.findByName(name).map(TagMapper::tagToTagDto);
  }

  public List<TagDto> getRecommendedTags(List<String> attachedTagIds) {
    if (attachedTagIds == null || attachedTagIds.isEmpty()) {
      return List.of();
    }

    return postTagRepository.findRecommendedTags(attachedTagIds).stream()
        .map(TagMapper::tagToTagDto)
        .toList();
  }

  public void attach(String postId, String tagId) {
    postTagRepository.attach(postId, tagId);
  }

  public void detach(String postId, String tagId) {
    postTagRepository.detach(postId, tagId);
  }
}
