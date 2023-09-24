package ee.fakeplastictrees.blog.core.exceptions;

import static java.lang.String.format;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, String resourceIdentifier) {
        super(format("%s was not found by '%s'", resource, resourceIdentifier));
    }

    public ResourceNotFoundException(Object resource, String resourceIdentifier) {
        super(format("%s was not found by '%s'", resource.getClass().getSimpleName(), resourceIdentifier));
    }
}
