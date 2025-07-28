package ee.fakeplastictrees.blog.post.model;

import java.time.Instant;

public record PostDto(String id, String title, String slug, String summary, String text, Instant date, Boolean visible) {
}
