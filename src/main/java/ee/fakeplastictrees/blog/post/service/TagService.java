package ee.fakeplastictrees.blog.post.service;

import static java.util.stream.Collectors.groupingBy;

import ee.fakeplastictrees.blog.post.model.PostTagDto;
import ee.fakeplastictrees.blog.post.repository.PostTagRepository;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class TagService {
  private final PostTagRepository postTagRepository;

  public TagService(PostTagRepository postTagRepository) {
    this.postTagRepository = postTagRepository;
  }

  public List<PostTagDto> getByPostId(String postId) {
    return postTagRepository.findByPostId(postId);
  }

  public Map<String, List<PostTagDto>> getByPostId(List<String> postIds) {
    return postTagRepository.findByPostIds(postIds).stream()
        .collect(groupingBy(PostTagDto::postId));
  }
}
