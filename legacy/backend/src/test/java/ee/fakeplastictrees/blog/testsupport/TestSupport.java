package ee.fakeplastictrees.blog.testsupport;

public class TestSupport {
    public static ApiExecutor apiExecutor(String baseUrl) {
        return new ApiExecutor(baseUrl);
    }

    public static ApiExecutor apiExecutor(String baseUrl, String token) {
        return new ApiExecutor(baseUrl, token);
    }
}
