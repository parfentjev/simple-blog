package ee.fakeplastictrees.blog.post;

import ee.fakeplastictrees.blog.post.model.*;

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
}
