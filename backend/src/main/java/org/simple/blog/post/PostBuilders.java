package org.simple.blog.post;

import org.simple.blog.post.model.PostDto;
import org.simple.blog.post.model.PostPreviewDto;

public class PostBuilders {
    public PostDto.PostDtoBuilder postDto() {
        return PostDto.builder();
    }

    public PostPreviewDto.PostPreviewDtoBuilder postPreviewDto() {
        return PostPreviewDto.builder();
    }
}
