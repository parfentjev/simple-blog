package ee.fakeplastictrees.blog.testsupport;

import ee.fakeplastictrees.blog.category.controller.request.PostCategoriesRequest;
import ee.fakeplastictrees.blog.category.controller.request.PutCategoriesRequest;
import ee.fakeplastictrees.blog.category.model.CategoryDto;
import ee.fakeplastictrees.blog.post.controller.request.PostPostsRequest;
import ee.fakeplastictrees.blog.post.controller.request.PutPostsRequest;
import ee.fakeplastictrees.blog.post.model.PostDto;
import ee.fakeplastictrees.blog.post.response.GetPostsResponse;
import ee.fakeplastictrees.blog.user.controller.request.PostUsersRequest;
import ee.fakeplastictrees.blog.user.controller.request.PostUsersTokenRequest;
import ee.fakeplastictrees.blog.user.model.TokenDto;
import ee.fakeplastictrees.blog.user.model.UserDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Optional;
import java.util.function.Function;

import static io.restassured.RestAssured.given;

public class ApiExecutor {
    private final String baseUrl;
    private final String token;

    public ApiExecutor(String baseUrl) {
        this.baseUrl = baseUrl;
        this.token = null;
    }

    public ApiExecutor(String baseUrl, String token) {
        this.baseUrl = baseUrl;
        this.token = token;
    }

    private RequestSpecification restAssured() {
        RequestSpecification requestSpecification = given().baseUri(baseUrl).log().everything();

        if (token != null) {
            requestSpecification.header("Authorization", "Bearer " + token);
        }

        return requestSpecification;
    }

    private RequestSpecification restAssured(Object object) {
        if (object == null) {
            return restAssured();
        }

        return restAssured().body(object).contentType(ContentType.JSON);
    }

    public <T> ExtendedResponse<T> requestBuilder(Function<RequestSpecification, Response> responseFunction, Class<T> responseClass) {
        return ExtendedResponse.of(responseFunction.apply(restAssured()), responseClass);
    }

    public ExtendedResponse<UserDto> postUsers(PostUsersRequest request) {
        return ExtendedResponse.of(restAssured(request).post("/users"), UserDto.class);
    }

    public ExtendedResponse<TokenDto> postUsersToken(PostUsersTokenRequest request) {
        return ExtendedResponse.of(restAssured(request).post("/users/token"), TokenDto.class);
    }

    public ExtendedResponse<CategoryDto[]> getCategories() {
        return ExtendedResponse.of(restAssured().get("/categories"), CategoryDto[].class);
    }

    public ExtendedResponse<CategoryDto> postCategories(PostCategoriesRequest request) {
        return ExtendedResponse.of(restAssured(request).post("/categories"), CategoryDto.class);
    }

    public ExtendedResponse<CategoryDto> putCategoriesById(String categoryId, PutCategoriesRequest request) {
        return ExtendedResponse.of(restAssured(request).put("/categories/" + categoryId), CategoryDto.class);
    }

    public ExtendedResponse<Void> deleteCategoriesById(String categoryId) {
        return ExtendedResponse.of(restAssured().delete("/categories/" + categoryId), Void.class);
    }

    public ExtendedResponse<GetPostsResponse> getPosts(Integer page, Integer size) {
        RequestSpecification requestSpecification = restAssured();
        Optional.ofNullable(page).ifPresent(value -> requestSpecification.queryParam("page", value));
        Optional.ofNullable(size).ifPresent(value -> requestSpecification.queryParam("size", value));

        return ExtendedResponse.of(requestSpecification.get("/posts"), GetPostsResponse.class);
    }

    public ExtendedResponse<PostDto> getPostsById(String postId) {
        return ExtendedResponse.of(restAssured().get("/posts/" + postId), PostDto.class);
    }

    public ExtendedResponse<PostDto> postPosts(PostPostsRequest request) {
        return ExtendedResponse.of(restAssured(request).post("/posts"), PostDto.class);
    }

    public ExtendedResponse<PostDto> putPostsById(String postId, PutPostsRequest request) {
        return ExtendedResponse.of(restAssured(request).put("/posts/" + postId), PostDto.class);
    }

    public ExtendedResponse<Void> deletePostsById(String postId) {
        return ExtendedResponse.of(restAssured().delete("/posts/" + postId), Void.class);
    }
}
