package ee.fakeplastictrees.blog.file.model;

import org.springframework.core.io.Resource;

public record ResourceDto(Resource resource, String contentType) {
}
