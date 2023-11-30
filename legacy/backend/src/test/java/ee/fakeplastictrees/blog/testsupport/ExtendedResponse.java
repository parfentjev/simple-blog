package ee.fakeplastictrees.blog.testsupport;

import ee.fakeplastictrees.blog.core.response.ErrorResponse;
import io.restassured.response.Response;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class ExtendedResponse<T> {
    private final Response response;
    private final Class<T> responseClass;

    public static <T> ExtendedResponse<T> of(Response response, Class<T> responseClass) {
        return new ExtendedResponse<>(response, responseClass);
    }

    private ExtendedResponse(Response response, Class<T> responseClass) {
        this.response = response;
        this.responseClass = responseClass;

        response.prettyPeek();
    }

    public ExtendedResponse<T> statusCode(int statusCode) {
        response.then().statusCode(statusCode);

        return this;
    }

    public ExtendedResponse<T> responseConsumer(Consumer<T> consumer) {
        consumer.accept(response.as(responseClass));

        return this;
    }

    public ExtendedResponse<T> message(String expectedMessage) {
        assertThat(response.as(ErrorResponse.class).getMessage()).isEqualTo(expectedMessage);

        return this;
    }

    public <R> R responseConsumer(Function<T, R> function) {
        return function.apply(response.as(responseClass));
    }

    public T body() {
        return response.as(responseClass);
    }
}
