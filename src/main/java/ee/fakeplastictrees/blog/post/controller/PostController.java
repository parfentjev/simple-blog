package ee.fakeplastictrees.blog.post.controller;

import ee.fakeplastictrees.blog.post.service.PostService;
import ee.fakeplastictrees.blog.post.service.TagService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PostController {
  private final PostService postService;
  private final TagService tagService;

  @Value("${posts.page.size:20}")
  private Integer pageSize;

  public PostController(PostService postService, TagService tagService) {
    this.postService = postService;
    this.tagService = tagService;
  }

  @GetMapping({"/", "/posts/{pageNumber}"})
  public String getPosts(@PathVariable(required = false) Integer pageNumber, Model model) {
    var page = postService.getPublishedPostsPreview(pageNumber == null ? 1 : pageNumber, pageSize);
    model.addAttribute("page", page);

    return "post/index";
  }

  @GetMapping({"/post/{postId}", "/post/{postId}/{slug}"})
  public String getPostById(
      @PathVariable String postId, @PathVariable(required = false) String slug, Model model) {
    var post = postService.getPublishedPost(postId);
    var tags = tagService.getByPostId(postId);

    model.addAttribute("post", post);
    model.addAttribute("tags", tags);

    return "post/full_post";
  }
}
