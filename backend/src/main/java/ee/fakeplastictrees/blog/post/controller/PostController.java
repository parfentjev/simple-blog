package ee.fakeplastictrees.blog.post.controller;

import ee.fakeplastictrees.blog.core.exceptions.ResourceNotFoundException;
import ee.fakeplastictrees.blog.core.model.PageDto;
import ee.fakeplastictrees.blog.post.model.PostDto;
import ee.fakeplastictrees.blog.post.model.PostPreviewDto;
import ee.fakeplastictrees.blog.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
