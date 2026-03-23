package ee.fakeplastictrees.blog.post.model;

import java.time.Instant;
import java.util.List;

public record PostPreviewDto(
    String id,
    String title,
    String slug,
    String summary,
    Instant date,
    Boolean visible,
    boolean hasMore,
    List<PostTagDto> tags) {}
