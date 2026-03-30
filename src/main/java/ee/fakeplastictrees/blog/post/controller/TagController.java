package ee.fakeplastictrees.blog.post.controller;

import ee.fakeplastictrees.blog.core.exception.HTTPNotFoundException;
import ee.fakeplastictrees.blog.core.model.PageDto;
import ee.fakeplastictrees.blog.post.service.PostService;
import ee.fakeplastictrees.blog.post.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TagController {
  private final TagService tagService;
  private final PostService postService;

  public TagController(TagService tagService, PostService postService) {
    this.tagService = tagService;
    this.postService = postService;
  }

  @GetMapping({"/tag/{slug}", "/tag/{slug}/{pageNumber}"})
  public String getTagBySlug(
      @PathVariable String slug, @PathVariable(required = false) Integer pageNumber, Model model) {
    var postIdsPage = tagService.getPostIdsBySlug(slug, pageNumber == null ? 1 : pageNumber);
    if (postIdsPage.getContent().isEmpty()) {
      throw new HTTPNotFoundException();
    }

    var posts = postService.getPublishedPostsPreview(postIdsPage.getContent());
    var currentPage = postIdsPage.getNumber() + 1;
    var postsPage = new PageDto<>(currentPage, postIdsPage.getTotalPages(), posts);
    model.addAttribute("page", postsPage);

    return "post/tags_list";
  }
}
