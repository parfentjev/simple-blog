package ee.fakeplastictrees.blog.testsupport;

import ee.fakeplastictrees.blog.category.controller.request.PostCategoriesRequest;
import ee.fakeplastictrees.blog.category.controller.request.PutCategoriesRequest;
import ee.fakeplastictrees.blog.category.model.CategoryDto;
import ee.fakeplastictrees.blog.user.controller.request.PostUsersRequest;
import ee.fakeplastictrees.blog.user.controller.request.PostUsersTokenRequest;
import ee.fakeplastictrees.blog.user.model.TokenDto;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

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

    public ExtendedResponse<TokenDto> postUsers(PostUsersRequest request) {
        return new ExtendedResponse<>(restAssured(request).post("/users"), TokenDto.class);
    }

    public ExtendedResponse<TokenDto> postUsersToken(PostUsersTokenRequest request) {
        return new ExtendedResponse<>(restAssured(request).post("/users/token"), TokenDto.class);
    }

    public ExtendedResponse<CategoryDto[]> getCategories() {
        return new ExtendedResponse<>(restAssured().get("/categories"), CategoryDto[].class);
    }

    public ExtendedResponse<CategoryDto> postCategories(PostCategoriesRequest request) {
        return new ExtendedResponse<>(restAssured(request).post("/categories"), CategoryDto.class);
    }

    public ExtendedResponse<CategoryDto> putCategoriesById(String categoryId, PutCategoriesRequest request) {
        return new ExtendedResponse<>(restAssured(request).put("/categories/" + categoryId), CategoryDto.class);
    }

    public ExtendedResponse<Void> deleteCategoriesById(String categoryId) {
        return new ExtendedResponse<>(restAssured().delete("/categories/" + categoryId), Void.class);
    }
}
