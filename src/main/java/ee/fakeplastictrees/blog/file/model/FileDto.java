package ee.fakeplastictrees.blog.file.model;

import jakarta.validation.constraints.NotBlank;
import java.time.Instant;

public record FileDto(String id, @NotBlank String filename, String contentType, Instant date) {}
