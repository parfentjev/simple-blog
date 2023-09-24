package ee.fakeplastictrees.blog.core;

import ee.fakeplastictrees.blog.post.PostMappers;
import ee.fakeplastictrees.blog.user.UserMappers;

public class Mappers {
    public PostMappers post() {
        return new PostMappers();
    }

    public UserMappers user() {
        return new UserMappers();
    }
}
