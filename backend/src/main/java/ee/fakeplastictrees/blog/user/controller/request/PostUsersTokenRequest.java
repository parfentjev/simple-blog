package ee.fakeplastictrees.blog.user.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostUsersTokenRequest {
    @NotBlank
    String username;

    @NotBlank
    String password;
}
