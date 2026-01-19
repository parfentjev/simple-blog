package ee.fakeplastictrees.blog.core.model;

import java.util.List;

public record PageDto<T>(Integer pageNumber, Integer totalPages, List<T> posts) {}
