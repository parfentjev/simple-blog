package ee.fakeplastictrees.blog.file.model;

import java.time.Instant;

public record FileDto(String id, String originalFilename, String contentType, Instant date) {
}
