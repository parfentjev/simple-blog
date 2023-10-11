package ee.fakeplastictrees.blog.testsupport;

import io.restassured.response.Response;

import java.util.function.Consumer;
import java.util.function.Function;

public class ExtendedResponse<T> {
    private final Response response;
    private final Class<T> responseClass;

    public ExtendedResponse(Response response, Class<T> responseClass) {
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

    public <R> R responseConsumer(Function<T, R> function) {
        return function.apply(response.as(responseClass));
    }

    public T body() {
        return response.as(responseClass);
    }
}
