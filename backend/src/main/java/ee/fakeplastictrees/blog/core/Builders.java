package ee.fakeplastictrees.blog.core;

import ee.fakeplastictrees.blog.post.PostBuilders;
import ee.fakeplastictrees.blog.core.model.PageDto;
import ee.fakeplastictrees.blog.core.response.ErrorResponse;

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
