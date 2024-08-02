package ee.fakeplastictrees.post.controller;

import ee.fakeplastictrees.core.model.PageDto;
import ee.fakeplastictrees.post.model.PostDto;
import ee.fakeplastictrees.post.model.PostPreviewDto;
import ee.fakeplastictrees.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/published")
public class PostPublicController {
  @Autowired
  private PostService postService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PageDto<PostPreviewDto>> getPosts(@RequestParam(value = "page", defaultValue = "1") Integer page) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(postService.getPublishedPosts(page));
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PostDto> getPostById(@PathVariable("id") String id) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(postService.getPost(id));
  }
}
