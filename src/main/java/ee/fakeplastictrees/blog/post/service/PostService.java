package ee.fakeplastictrees.blog.post.service;

import ee.fakeplastictrees.blog.core.exception.ResourceNotFoundException;
import ee.fakeplastictrees.blog.core.model.factory.PageRequestFactory;
import ee.fakeplastictrees.blog.post.model.*;
import ee.fakeplastictrees.blog.post.model.mapper.PostMapper;
import ee.fakeplastictrees.blog.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Service
public class PostService {
  private static final int PAGE_SIZE = 20;

  private final PostRepository postRepository;

  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public PostPageDto getPublishedPosts(Integer pageNumber) {
    var postsPage = postRepository.findByVisible(PageRequestFactory.withPage(pageNumber, PAGE_SIZE, "date"), true);

    return new PostPageDto(pageNumber, postsPage.getTotalPages(), postsPage.get().map(PostMapper::postToPreviewDto).toList());
  }

  public PostDto getPublishedPost(String id) {
    var post = postRepository.findVisibleById(id).orElseThrow(ResourceNotFoundException::new);

    return PostMapper.postToDto(post);
  }

  public PostPageDto getEditorPosts(Integer pageNumber) {
    var postsPage = postRepository.findAll(PageRequestFactory.withPage(pageNumber, PAGE_SIZE, "date"));

    return new PostPageDto(pageNumber, postsPage.getTotalPages(), postsPage.get().map(PostMapper::postToPreviewDto).toList());
  }

  public PostDto getEditorPost(String id) {
    var post = postRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

    return PostMapper.postToDto(post);
  }

  public PostDto createPost(PostEditorDto postEditorDto) {
    var post = PostMapper.editorDtoToPost(postEditorDto);
    post.setSlug(encodeTitle(post.getTitle()));
    post.setDate(Instant.now());

    return PostMapper.postToDto(postRepository.save(post));
  }

  public PostDto updatePost(PostEditorDto postEditorDto) {
    var post = postRepository.findById(postEditorDto.id()).orElseThrow(ResourceNotFoundException::new);
    post.setTitle(postEditorDto.title());
    post.setSummary(postEditorDto.summary());
    post.setText(postEditorDto.text());
    post.setVisible(postEditorDto.visible() != null && postEditorDto.visible());
    post.setDate(postEditorDto.updateDate() != null && postEditorDto.updateDate() ? Instant.now() : post.getDate());

    return PostMapper.postToDto(postRepository.save(post));
  }

  public void deletePost(String id) {
    postRepository.deleteById(id);
  }

  private String encodeTitle(String title) {
    return URLEncoder.encode(title
      .replaceAll(" ", "-")
      .replaceAll("\\.", "")
      .replaceAll("\\?", "")
      .replaceAll("!", "")
      .replaceAll("#", "")
      .replaceAll("@", "")
      .replaceAll(":", "")
      .replaceAll(",", "")
      .replaceAll("\"", "")
      .replaceAll("&", "and")
      .toLowerCase(), StandardCharsets.UTF_8
    );
  }
}
