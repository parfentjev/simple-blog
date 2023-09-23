package ee.fakeplastictrees.blog.post;

import ee.fakeplastictrees.blog.post.model.PostDto;
import ee.fakeplastictrees.blog.post.model.PostPreviewDto;

public class PostBuilders {
    public PostDto.PostDtoBuilder postDto() {
        return PostDto.builder();
    }

    public PostPreviewDto.PostPreviewDtoBuilder postPreviewDto() {
        return PostPreviewDto.builder();
    }
}
