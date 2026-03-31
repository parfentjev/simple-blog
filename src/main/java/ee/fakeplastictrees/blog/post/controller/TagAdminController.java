package ee.fakeplastictrees.blog.post.controller;

import ee.fakeplastictrees.blog.core.annotation.ProtectedRoute;
import ee.fakeplastictrees.blog.post.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class TagAdminController {
  private final TagService tagService;

  public TagAdminController(TagService tagService) {
    this.tagService = tagService;
  }

  @PostMapping("/tag/attach")
  @ProtectedRoute
  public String attachTag(
      @RequestParam("post_id") String postId, @RequestParam("tag_id") String tagId) {
    tagService.attach(postId, tagId);

    return "redirect:/admin/post/" + postId;
  }

  @PostMapping("/tag/detach")
  @ProtectedRoute
  public String detachTag(
      @RequestParam("post_id") String postId, @RequestParam("tag_id") String tagId) {
    tagService.detach(postId, tagId);

    return "redirect:/admin/post/" + postId;
  }
}
