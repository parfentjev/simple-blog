package ee.fakeplastictrees.blog.post.controller;

import ee.fakeplastictrees.blog.core.annotation.ProtectedRoute;
import ee.fakeplastictrees.blog.post.model.PostEditorDto;
import ee.fakeplastictrees.blog.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class PostAdminController {
  private final PostService postService;

  public PostAdminController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping({"/posts", "/posts/{pageNumber}"})
  @ProtectedRoute
  public String getPostList(@PathVariable(required = false) Integer pageNumber, Model model) {
    var page = postService.getEditorPosts(pageNumber == null ? 1 : pageNumber);
    model.addAttribute("page", page);

    return "admin/post/post_list";
  }

  @GetMapping("/post/{postId}")
  @ProtectedRoute
  public String getPostById(@PathVariable String postId, Model model) {
    var post = postService.getEditorPost(postId);
    model.addAttribute("post", post);

    return "admin/post/post_edit";
  }

  @PostMapping("/post/{postId}")
  @ProtectedRoute
  public String updatePostById(@ModelAttribute PostEditorDto postEditorDto, @PathVariable String postId, RedirectAttributes model) {
    var post = postService.updatePost(postEditorDto);
    model.addAttribute("post", post);

    return "redirect:/admin/post/" + postId;
  }

  @GetMapping("/new-post")
  @ProtectedRoute
  public String newPostForm() {
    return "admin/post/post_create";
  }

  @PostMapping("/post")
  @ProtectedRoute
  public String createPost(@ModelAttribute PostEditorDto postEditorDto, Model model) {
    var post = postService.createPost(postEditorDto);
    model.addAttribute("post", post);

    return "redirect:/admin/post/" + post.id();
  }

  @PostMapping("/post/{postId}/delete")
  @ProtectedRoute
  public String deletePost(@PathVariable String postId) {
    postService.deletePost(postId);

    return "redirect:/admin/posts";
  }
}
