package ee.fakeplastictrees.blog.post.model;

import java.util.List;

public record PostPageDto(Integer pageNumber, Integer totalPages, List<PostPreviewDto> posts) {}
