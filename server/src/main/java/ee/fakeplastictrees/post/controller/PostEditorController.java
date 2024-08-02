package ee.fakeplastictrees.post.controller;

import ee.fakeplastictrees.core.annotation.PostEditor;
import ee.fakeplastictrees.core.model.PageDto;
import ee.fakeplastictrees.post.model.PostDto;
import ee.fakeplastictrees.post.model.PostEditorDto;
import ee.fakeplastictrees.post.model.PostPreviewDto;
import ee.fakeplastictrees.post.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/editor")
@PostEditor
public class PostEditorController {
  @Autowired
  private PostService postService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PageDto<PostPreviewDto>> getPosts(@RequestParam(value = "page", defaultValue = "1") Integer page) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(postService.getPosts(page));
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostEditorDto request) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(postService.createPost(request));
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PostDto> getPostById(@PathVariable("id") String id) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(postService.getPost(id));
  }

  @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PostDto> updatePostById(@PathVariable("id") String id, @Valid @RequestBody PostEditorDto request) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(postService.updatePost(id, request));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<PostDto> deletePostById(@PathVariable("id") String id) {
    postService.deletePost(id);

    return ResponseEntity
      .status(HttpStatus.OK)
      .build();
  }
}
