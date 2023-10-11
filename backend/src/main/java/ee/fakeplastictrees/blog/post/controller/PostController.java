package ee.fakeplastictrees.blog.post.controller;

import ee.fakeplastictrees.blog.core.annotation.AuthorizedEditor;
import ee.fakeplastictrees.blog.core.exceptions.ResourceNotFoundException;
import ee.fakeplastictrees.blog.core.model.PageDto;
import ee.fakeplastictrees.blog.post.controller.request.PostPostsRequest;
import ee.fakeplastictrees.blog.post.controller.request.PutPostsRequest;
import ee.fakeplastictrees.blog.post.model.PostDto;
import ee.fakeplastictrees.blog.post.model.PostPreviewDto;
import ee.fakeplastictrees.blog.post.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static ee.fakeplastictrees.blog.core.Utils.builders;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageDto<PostPreviewDto>> getPosts(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (size > 100) {
            size = 100;
        }

        return new ResponseEntity<>(postService.getPosts(page, size), HttpStatus.OK);
    }

    @GetMapping(value = "/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDto> getPostBy(@PathVariable("postId") String postId) {
        return postService.getPost(postId)
                .map(postDto -> new ResponseEntity<>(postDto, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Post", postId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @AuthorizedEditor
    public ResponseEntity<PostDto> postPosts(@Valid @RequestBody PostPostsRequest request) {
        PostDto postDto = builders().post().postDto()
                .title(request.getTitle())
                .summary(request.getSummary())
                .text(request.getText())
                .date(request.getDate())
                .visible(request.getVisible())
                .category(request.getCategory())
                .build();

        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @AuthorizedEditor
    public ResponseEntity<PostDto> putPosts(@PathVariable("postId") String postId, @Valid @RequestBody PutPostsRequest request) {
        PostDto postDto = builders().post().postDto()
                .id(postId)
                .title(request.getTitle())
                .summary(request.getSummary())
                .text(request.getText())
                .date(request.getDate())
                .visible(request.getVisible())
                .category(request.getCategory())
                .build();

        return new ResponseEntity<>(postService.updatePost(postDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{postId}")
    @AuthorizedEditor
    public ResponseEntity<Void> deletePosts(@PathVariable("postId") String postId) {
        postService.deletePost(postId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}