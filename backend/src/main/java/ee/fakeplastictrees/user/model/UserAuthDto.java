package ee.fakeplastictrees.user.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAuthDto {
  @NotNull
  @Size(min = 3, max = 25, message = "must be between 3 and 25 characters")
  String username;

  @NotNull
  @Size(min = 8, max = 1024, message = "must be between 8 and 1024 characters")
  String password;
}
