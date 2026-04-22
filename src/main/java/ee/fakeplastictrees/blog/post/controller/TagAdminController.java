package ee.fakeplastictrees.blog.post.controller;

import ee.fakeplastictrees.blog.core.annotation.ProtectedRoute;
import ee.fakeplastictrees.blog.core.exception.HTTPNotFoundException;
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
      @RequestParam("post_id") String postId,
      @RequestParam(name = "tag_id", required = false) String tagId,
      @RequestParam(name = "tag_name", required = false) String tagName) {
    if (tagId != null && !tagId.isBlank()) {
      tagService.attach(postId, tagId);
    } else if (tagName != null && !tagName.isBlank()) {
      var tag = tagService.getTagByName(tagName);
      if (tag.isEmpty()) {
        throw new HTTPNotFoundException("todo: suggest to create it...");
      }

      tagService.attach(postId, tag.get().id());
    }

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
