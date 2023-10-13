package ee.fakeplastictrees.blog.post.controller.request;

public class PostRequestBuilders {
    public PostPostsRequest.PostPostsRequestBuilder postPosts() {
        return PostPostsRequest.builder();
    }

    public PutPostsRequest.PutPostsRequestBuilder putPosts() {
        return PutPostsRequest.builder();
    }
}
