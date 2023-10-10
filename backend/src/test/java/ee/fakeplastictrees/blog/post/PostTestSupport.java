package ee.fakeplastictrees.blog.post;

import ee.fakeplastictrees.blog.post.model.Post;
import ee.fakeplastictrees.blog.post.model.PostDto;
import ee.fakeplastictrees.blog.post.model.PostPreviewDto;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Set;

import static ee.fakeplastictrees.blog.core.Utils.builders;

public class PostTestSupport {
    public Post generatePost() {
        return builders().post().post()
                .id(RandomStringUtils.randomAlphanumeric(10))
                .title(RandomStringUtils.randomAlphanumeric(10))
                .summary(RandomStringUtils.randomAlphanumeric(10))
                .text(RandomStringUtils.randomAlphanumeric(10))
                .date(RandomStringUtils.randomAlphanumeric(10))
                .visible(true)
                .category(Set.of(RandomStringUtils.randomAlphanumeric(10), RandomStringUtils.randomAlphanumeric(10)))
                .build();
    }

    public PostDto generatePostDto() {
        return builders().post().postDto()
                .id(RandomStringUtils.randomAlphanumeric(10))
                .title(RandomStringUtils.randomAlphanumeric(10))
                .summary(RandomStringUtils.randomAlphanumeric(10))
                .text(RandomStringUtils.randomAlphanumeric(10))
                .date(RandomStringUtils.randomAlphanumeric(10))
                .visible(true)
                .category(Set.of(RandomStringUtils.randomAlphanumeric(10), RandomStringUtils.randomAlphanumeric(10)))
                .build();
    }

    public PostPreviewDto generatePostPreviewDto() {
        return builders().post().postPreviewDto()
                .id(RandomStringUtils.randomAlphanumeric(10))
                .title(RandomStringUtils.randomAlphanumeric(10))
                .summary(RandomStringUtils.randomAlphanumeric(10))
                .date(RandomStringUtils.randomAlphanumeric(10))
                .category(Set.of(RandomStringUtils.randomAlphanumeric(10), RandomStringUtils.randomAlphanumeric(10)))
                .build();
    }
}
