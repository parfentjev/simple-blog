package ee.fakeplastictrees.blog.user.controller.request;

public class UserRequestBuilders {
    public PostUsersRequest.PostUsersRequestBuilder postUsers() {
        return PostUsersRequest.builder();
    }

    public PostUsersTokenRequest.PostUsersTokenRequestBuilder postUsersToken() {
        return PostUsersTokenRequest.builder();
    }
}
