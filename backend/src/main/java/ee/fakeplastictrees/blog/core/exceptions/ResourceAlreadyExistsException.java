package ee.fakeplastictrees.blog.core.exceptions;

import static java.lang.String.format;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String resource, String resourceIdentifier) {
        super(format("%s with identifier '%s' already exists.", resource, resourceIdentifier));
    }

    public ResourceAlreadyExistsException(Class<?> resource, String resourceIdentifier) {
        super(format("%s with identifier '%s' already exists.", resource.getSimpleName(), resourceIdentifier));
    }
}
