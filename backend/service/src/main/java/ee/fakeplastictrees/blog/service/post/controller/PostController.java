package ee.fakeplastictrees.blog.service.post.controller;

import ee.fakeplastictrees.blog.codegen.api.PostsApi;
import ee.fakeplastictrees.blog.codegen.model.PagePostDto;
import ee.fakeplastictrees.blog.codegen.model.PostDto;
import ee.fakeplastictrees.blog.codegen.model.PostEditorDto;
import ee.fakeplastictrees.blog.service.core.annotation.RequireRoleEditor;
import ee.fakeplastictrees.blog.service.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController implements PostsApi {
  @Autowired
  private PostService postService;

  @Override
  @RequireRoleEditor
  public ResponseEntity<PagePostDto> postsEditorGet(Integer page) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(postService.getPosts(page));
  }

  @Override
  @RequireRoleEditor
  public ResponseEntity<Void> postsEditorIdDelete(String id) {
    postService.deletePost(id);

    return ResponseEntity
      .status(HttpStatus.OK)
      .build();
  }

  @Override
  @RequireRoleEditor
  public ResponseEntity<PostDto> postsEditorIdGet(String id) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(postService.getPost(id));
  }

  @Override
  @RequireRoleEditor
  public ResponseEntity<PostDto> postsEditorIdPut(String id, PostEditorDto request) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(postService.updatePost(id, request));
  }

  @Override
  @RequireRoleEditor
  public ResponseEntity<PostDto> postsEditorPost(PostEditorDto request) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(postService.createPost(request));
  }

  @Override
  public ResponseEntity<PagePostDto> postsPublishedGet(Integer page) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(postService.getPublishedPosts(page));
  }

  @Override
  public ResponseEntity<PostDto> postsPublishedIdGet(String id) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(postService.getPost(id));
  }
}
