package ee.fakeplastictrees.blog.service.post.service;

import ee.fakeplastictrees.blog.codegen.model.PagePostDto;
import ee.fakeplastictrees.blog.codegen.model.PostDto;
import ee.fakeplastictrees.blog.codegen.model.PostEditorDto;
import ee.fakeplastictrees.blog.service.core.model.PageRequestFactory;
import ee.fakeplastictrees.blog.service.post.model.PostExceptionFactory;
import ee.fakeplastictrees.blog.service.post.model.PostRepository;
import ee.fakeplastictrees.blog.service.post.util.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
  private static final int PAGE_SIZE = 20;

  @Autowired
  private PostRepository postRepository;

  public PagePostDto getPosts(Integer page) {
    var postsPage = postRepository.findAll(PageRequestFactory.withPage(page, PAGE_SIZE, "date"));

    return new PagePostDto()
      .page(page)
      .totalPages(postsPage.getTotalPages())
      .items(postsPage.get().map(PostMapper::postToPreviewDto).toList());
  }

  public PagePostDto getPublishedPosts(Integer page) {
    var postsPage = postRepository.findByVisible(PageRequestFactory.withPage(page, PAGE_SIZE, "date"), true);

    return new PagePostDto()
      .page(page)
      .totalPages(postsPage.getTotalPages())
      .items(postsPage.get().map(PostMapper::postToPreviewDto).toList());
  }

  public PostDto getPost(String id) {
    var post = postRepository.findById(id).stream().findFirst().orElseThrow(PostExceptionFactory::notFound);

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
