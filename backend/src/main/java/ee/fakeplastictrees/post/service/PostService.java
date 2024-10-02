package ee.fakeplastictrees.post.service;

import ee.fakeplastictrees.core.model.PageDto;
import ee.fakeplastictrees.post.exception.PostNotFoundException;
import ee.fakeplastictrees.post.model.PostDto;
import ee.fakeplastictrees.post.model.PostEditorDto;
import ee.fakeplastictrees.post.model.PostPreviewDto;
import ee.fakeplastictrees.post.repository.PostRepository;
import ee.fakeplastictrees.post.util.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostService {
  private static final int PAGE_SIZE = 20;

  @Autowired
  private PostRepository postRepository;

  public PageDto<PostPreviewDto> getPosts(Integer page) {
    var postsPage = postRepository.findAll(pageRequest(page));

    return PageDto.<PostPreviewDto>builder()
      .page(page)
      .totalPages(postsPage.getTotalPages())
      .items(postsPage.get().map(PostMapper::postToPreviewDto).toList())
      .build();
  }

  public PageDto<PostPreviewDto> getPublishedPosts(Integer page) {
    var postsPage = postRepository.findByVisible(pageRequest(page), true);

    return PageDto.<PostPreviewDto>builder()
      .page(page)
      .totalPages(postsPage.getTotalPages())
      .items(postsPage.get().map(PostMapper::postToPreviewDto).toList())
      .build();
  }

  private PageRequest pageRequest(int page) {
    return PageRequest.of(page - 1, PAGE_SIZE).withSort(Sort.by("date").descending());
  }

  public PostDto getPost(String id) {
    var post = postRepository.findById(id).stream().findFirst().orElseThrow(PostNotFoundException::new);

    return PostMapper.postToDto(post);
  }

  public PostDto createPost(PostEditorDto postEditorDto) {
    var post = PostMapper.editorDtoToPost(postEditorDto);
    post.setId(null);

    return PostMapper.postToDto(postRepository.save(post));
  }

  public PostDto updatePost(String id, PostEditorDto postEditorDto) {
    var post = PostMapper.editorDtoToPost(postEditorDto);
    post.setId(id);

    return PostMapper.postToDto(postRepository.save(post));
  }

  public void deletePost(String id) {
    postRepository.deleteById(id);
  }
}
