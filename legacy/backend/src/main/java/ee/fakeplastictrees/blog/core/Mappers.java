package ee.fakeplastictrees.blog.core;

import ee.fakeplastictrees.blog.category.CategoryMappers;
import ee.fakeplastictrees.blog.post.PostMappers;
import ee.fakeplastictrees.blog.user.UserMappers;

public class Mappers {
    public PostMappers post() {
        return new PostMappers();
    }

    public CategoryMappers category() {
        return new CategoryMappers();
    }

    public UserMappers user() {
        return new UserMappers();
    }
}
