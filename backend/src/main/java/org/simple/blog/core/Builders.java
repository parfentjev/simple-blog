package org.simple.blog.core;

import org.simple.blog.core.model.PageDto;
import org.simple.blog.core.response.ErrorResponse;
import org.simple.blog.post.PostBuilders;

public class Builders {
    public ErrorResponse.ErrorResponseBuilder errorResponse() {
        return ErrorResponse.builder();
    }

    public <T> PageDto.PageDtoBuilder<T> pageDto() {
        return PageDto.builder();
    }

    public PostBuilders post() {
        return new PostBuilders();
    }
}
