package ee.fakeplastictrees.blog.file.model;

import java.util.List;

public record FilePageDto(Integer pageNumber, Integer totalPages, List<FileDto> files) {}
