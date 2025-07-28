package ee.fakeplastictrees.blog.post.model;

import java.time.Instant;

public record PostPreviewDto(String id, String title, String slug, String summary, Instant date,Boolean visible) {
}
