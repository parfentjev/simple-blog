package ee.fakeplastictrees.blog.post.service;

import ee.fakeplastictrees.blog.core.exception.HTTPNotFoundException;
import ee.fakeplastictrees.blog.core.model.factory.PageRequestFactory;
import ee.fakeplastictrees.blog.post.model.PostDto;
import ee.fakeplastictrees.blog.post.model.PostEditorDto;
import ee.fakeplastictrees.blog.post.model.PostPageDto;
import ee.fakeplastictrees.blog.post.model.mapper.PostMapper;
import ee.fakeplastictrees.blog.post.repository.PostRepository;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PostService {
  private final PostRepository postRepository;

  @Value("${posts.admin.page.size:100}")
  private Integer adminPageSize;

  private static final String sortBy = "date";

  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public PostPageDto getPublishedPosts(Integer pageNumber, Integer pageSize) {
    var pageable = PageRequestFactory.withPage(pageNumber, pageSize, sortBy);
    var postsPage = postRepository.findByVisible(pageable, true);

    return new PostPageDto(
        pageNumber,
        postsPage.getTotalPages(),
        postsPage.get().map(PostMapper::postToPreviewDto).toList());
  }

  public PostDto getPublishedPost(String id) {
    var post = postRepository.findVisibleById(id).orElseThrow(HTTPNotFoundException::new);

    return PostMapper.postToDto(post);
  }

  public PostPageDto getEditorPosts(Integer pageNumber) {
    var pageable = PageRequestFactory.withPage(pageNumber, adminPageSize, sortBy);
    var postsPage = postRepository.findAll(pageable);

    return new PostPageDto(
        pageNumber,
        postsPage.getTotalPages(),
        postsPage.get().map(PostMapper::postToPreviewDto).toList());
  }

  public PostDto getEditorPost(String id) {
    var post = postRepository.findById(id).orElseThrow(HTTPNotFoundException::new);

    return PostMapper.postToDto(post);
  }

  public PostDto createPost(PostEditorDto postEditorDto) {
    var post = PostMapper.editorDtoToPost(postEditorDto);
    post.setSlug(encodeTitle(post.getTitle()));
    post.setDate(Instant.now());

    return PostMapper.postToDto(postRepository.save(post));
  }

  public PostDto updatePost(PostEditorDto postEditorDto) {
    var post = postRepository.findById(postEditorDto.id()).orElseThrow(HTTPNotFoundException::new);

    var date =
        postEditorDto.updateDate() != null && postEditorDto.updateDate()
            ? Instant.now()
            : post.getDate();

    post.setTitle(postEditorDto.title());
    post.setSummary(postEditorDto.summary());
    post.setText(postEditorDto.text());
    post.setVisible(postEditorDto.visible() != null && postEditorDto.visible());
    post.setDate(date);

    return PostMapper.postToDto(postRepository.save(post));
  }

  public void deletePost(String id) {
    postRepository.deleteById(id);
  }

  private String encodeTitle(String title) {
    return URLEncoder.encode(
        title
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
            .toLowerCase(),
        StandardCharsets.UTF_8);
  }
}
