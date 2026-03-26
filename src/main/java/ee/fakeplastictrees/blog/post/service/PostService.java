package ee.fakeplastictrees.blog.post.service;

import static ee.fakeplastictrees.blog.core.model.factory.PageRequestFactory.pageable;
import static ee.fakeplastictrees.blog.post.model.mapper.PostMapper.postToDto;
import static ee.fakeplastictrees.blog.post.model.mapper.PostMapper.postToPreviewDto;
import static java.nio.charset.StandardCharsets.UTF_8;

import ee.fakeplastictrees.blog.core.exception.HTTPNotFoundException;
import ee.fakeplastictrees.blog.core.model.PageDto;
import ee.fakeplastictrees.blog.post.model.Post;
import ee.fakeplastictrees.blog.post.model.PostDto;
import ee.fakeplastictrees.blog.post.model.PostEditorDto;
import ee.fakeplastictrees.blog.post.model.PostPreviewDto;
import ee.fakeplastictrees.blog.post.model.mapper.PostMapper;
import ee.fakeplastictrees.blog.post.repository.PostRepository;
import java.net.URLEncoder;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PostService {
  private static final String sortBy = "date";

  private final PostRepository postRepository;
  private final TagService tagService;

  public PostService(PostRepository postRepository, TagService tagService) {
    this.postRepository = postRepository;
    this.tagService = tagService;
  }

  public PageDto<PostPreviewDto> getPublishedPostsPreview(Integer pageNumber, Integer pageSize) {
    var pageable = pageable(pageNumber, pageSize, sortBy);
    var postsPage = postRepository.findPublished(pageable);

    var postIds = postsPage.getContent().stream().map(Post::getId).toList();
    var tags = tagService.getByPostId(postIds);

    return new PageDto<PostPreviewDto>(
        pageNumber,
        postsPage.getTotalPages(),
        postsPage
            .get()
            .map(post -> postToPreviewDto(post, tags.getOrDefault(post.getId(), List.of())))
            .toList());
  }

  public PageDto<PostDto> getPublishedPostsFull(Integer pageNumber, Integer pageSize) {
    var pageable = pageable(pageNumber, pageSize, sortBy);
    var postsPage = postRepository.findPublished(pageable);

    return new PageDto<PostDto>(
        pageNumber, postsPage.getTotalPages(), postsPage.get().map(PostMapper::postToDto).toList());
  }

  public PostDto getPublishedPost(String id) {
    var post = postRepository.findPublishedById(id).orElseThrow(HTTPNotFoundException::new);

    return postToDto(post);
  }

  public PageDto<PostPreviewDto> getEditorPosts(Integer pageNumber, Integer pageSize) {
    var pageable = pageable(pageNumber, pageSize, sortBy);
    var postsPage = postRepository.findAll(pageable);

    return new PageDto<PostPreviewDto>(
        pageNumber,
        postsPage.getTotalPages(),
        postsPage.get().map(PostMapper::postToPreviewDto).toList());
  }

  public PostDto getEditorPost(String id) {
    var post = postRepository.findById(id).orElseThrow(HTTPNotFoundException::new);

    return postToDto(post);
  }

  public PostDto createPost(PostEditorDto postEditorDto) {
    var post = PostMapper.editorDtoToPost(postEditorDto);
    post.setSlug(encodeTitle(post.getTitle()));
    post.setDate(Instant.now());

    return postToDto(postRepository.save(post));
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

    return postToDto(postRepository.save(post));
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
            .replaceAll("&", "-")
            .toLowerCase(),
        UTF_8);
  }
}
