package ee.fakeplastictrees.blog.user.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequestDto(
  @NotBlank
  @Size(max = 255)
  String username,
  @NotBlank
  @Size(min = 32)
  String password) {
}
