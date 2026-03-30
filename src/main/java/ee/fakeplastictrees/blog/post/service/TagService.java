package ee.fakeplastictrees.blog.post.service;

import static ee.fakeplastictrees.blog.core.model.factory.PageRequestFactory.pageable;
import static java.util.stream.Collectors.groupingBy;

import ee.fakeplastictrees.blog.post.model.PostTagDto;
import ee.fakeplastictrees.blog.post.repository.PostTagRepository;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TagService {
  private final PostTagRepository postTagRepository;

  @Value("${posts.page.size:20}")
  private Integer pageSize;

  public TagService(PostTagRepository postTagRepository) {
    this.postTagRepository = postTagRepository;
  }

  public List<PostTagDto> getTagsByPostId(String postId) {
    return postTagRepository.findByPostId(postId);
  }

  public Map<String, List<PostTagDto>> getTagsByPostId(List<String> postIds) {
    return postTagRepository.findByPostId(postIds).stream().collect(groupingBy(PostTagDto::postId));
  }

  public Page<String> getPostIdsBySlug(String slug, Integer pageNumber) {
    var pageable = pageable(pageNumber, pageSize, "p.date");

    return postTagRepository.findPostIdsBySlug(slug, pageable);
  }
}
