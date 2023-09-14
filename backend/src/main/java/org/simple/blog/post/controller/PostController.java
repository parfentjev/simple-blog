package org.simple.blog.post.controller;

import org.simple.blog.core.model.PageDto;
import org.simple.blog.post.model.PostDto;
import org.simple.blog.post.model.PostPreviewDto;
import org.simple.blog.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/posts"})
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageDto<PostPreviewDto>> getPosts(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (size > 10) {
            size = 10;
        }

        return new ResponseEntity<>(postService.getPosts(page, size), HttpStatus.OK);
    }

    @GetMapping(value = "/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDto> getPostBy(@PathVariable("postId") String postId) {
        return postService.getPost(postId)
                .map(postDto -> new ResponseEntity<>(postDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}
