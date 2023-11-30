package ee.fakeplastictrees.blog.post;

import ee.fakeplastictrees.blog.post.controller.request.PostRequestBuilders;
import ee.fakeplastictrees.blog.post.model.Post;
import ee.fakeplastictrees.blog.post.model.PostDto;
import ee.fakeplastictrees.blog.post.model.PostPreviewDto;

public class PostBuilders {
    public PostDto.PostDtoBuilder postDto() {
        return PostDto.builder();
    }

    public Post.PostBuilder post() {
        return Post.builder();
    }

    public PostPreviewDto.PostPreviewDtoBuilder postPreviewDto() {
        return PostPreviewDto.builder();
    }

    public PostRequestBuilders request() {
        return new PostRequestBuilders();
    }
}
