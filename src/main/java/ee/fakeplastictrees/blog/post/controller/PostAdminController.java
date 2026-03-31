package ee.fakeplastictrees.blog.post.controller;

import ee.fakeplastictrees.blog.core.annotation.ProtectedRoute;
import ee.fakeplastictrees.blog.post.model.PostEditorDto;
import ee.fakeplastictrees.blog.post.service.PostService;
import ee.fakeplastictrees.blog.post.service.TagService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class PostAdminController {
  private final PostService postService;
  private final TagService tagService;

  @Value("${posts.admin.page.size:100}")
  private Integer pageSize;

  public PostAdminController(PostService postService, TagService tagService) {
    this.postService = postService;
    this.tagService = tagService;
  }

  @GetMapping({"/posts", "/posts/{pageNumber}"})
  @ProtectedRoute
  public String getPostList(@PathVariable(required = false) Integer pageNumber, Model model) {
    var page = postService.getEditorPosts(pageNumber == null ? 1 : pageNumber, pageSize);
    model.addAttribute("page", page);

    return "admin/post/post_list";
  }

  @GetMapping("/post/{postId}")
  @ProtectedRoute
  public String getPostById(@PathVariable String postId, Model model) {
    var post = postService.getEditorPost(postId);
    var tags = tagService.getTagsByPostId(postId);

    model.addAttribute("post", post);
    model.addAttribute("tags", tags);

    return "admin/post/post_editor";
  }

  @PostMapping("/post/{postId}")
  @ProtectedRoute
  public String updatePostById(
      @ModelAttribute PostEditorDto postEditorDto, @PathVariable String postId) {
    postService.updatePost(postEditorDto);

    return "redirect:/admin/post/" + postId;
  }

  @GetMapping("/new-post")
  @ProtectedRoute
  public String newPostForm() {
    return "admin/post/post_create";
  }

  @PostMapping("/post")
  @ProtectedRoute
  public String createPost(@ModelAttribute PostEditorDto postEditorDto) {
    var post = postService.createPost(postEditorDto);

    return "redirect:/admin/post/" + post.id();
  }

  @PostMapping("/post/{postId}/delete")
  @ProtectedRoute
  public String deletePost(@PathVariable String postId) {
    postService.deletePost(postId);

    return "redirect:/admin/posts";
  }
}
