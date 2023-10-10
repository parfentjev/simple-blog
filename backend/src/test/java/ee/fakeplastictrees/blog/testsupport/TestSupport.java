package ee.fakeplastictrees.blog.testsupport;

import ee.fakeplastictrees.blog.category.CategoryTestSupport;
import ee.fakeplastictrees.blog.post.PostTestSupport;

public class TestSupport {
    public static CategoryTestSupport categoryTestSupport() {
        return new CategoryTestSupport();
    }

    public static PostTestSupport postTestSupport() {
        return new PostTestSupport();
    }
}
