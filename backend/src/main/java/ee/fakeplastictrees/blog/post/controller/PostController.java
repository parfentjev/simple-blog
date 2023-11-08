package ee.fakeplastictrees.blog.post.controller;

import ee.fakeplastictrees.blog.core.annotation.AuthorizedEditor;
import ee.fakeplastictrees.blog.core.exceptions.ResourceNotFoundException;
import ee.fakeplastictrees.blog.core.model.PageDto;
import ee.fakeplastictrees.blog.post.controller.request.PostPostsRequest;
import ee.fakeplastictrees.blog.post.controller.request.PutPostsRequest;
import ee.fakeplastictrees.blog.post.model.PostDto;
import ee.fakeplastictrees.blog.post.model.PostPreviewDto;
import ee.fakeplastictrees.blog.post.service.PostService;
import ee.fakeplastictrees.blog.user.model.UserPrivilege;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static ee.fakeplastictrees.blog.core.Utils.builders;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageDto<PostPreviewDto>> getPosts(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                            @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                            @RequestParam(value = "category", required = false) String category) {
        if (size > 100) {
            size = 100;
        }

        PageDto<PostPreviewDto> posts;
        if (category == null) {
            posts = postService.getPosts(page, size);
        } else {
            posts = postService.getPosts(page, size, category);
        }

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping(value = "/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") String postId, Authentication authentication) {
        boolean includeDrafts = UserPrivilege.POST_MANAGEMENT.isGranted(authentication);

        return postService.getPost(postId, includeDrafts)
                .map(postDto -> new ResponseEntity<>(postDto, HttpStatus.OK)).
                orElseThrow(() -> new ResourceNotFoundException("Post", postId));
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
    public ResponseEntity<PostDto> putPostsById(@PathVariable("postId") String postId, @Valid @RequestBody PutPostsRequest request) {
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
    public ResponseEntity<Void> deletePostsById(@PathVariable("postId") String postId) {
        postService.deletePost(postId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
