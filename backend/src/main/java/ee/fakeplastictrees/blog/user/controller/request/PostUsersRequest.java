package ee.fakeplastictrees.blog.user.controller.request;

import ee.fakeplastictrees.blog.user.model.UserRole;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostUsersRequest {
    @Size(min = 1, max = 32)
    @NotNull
    String username;

    @Size(min = 1, max = 128)
    @NotNull
    String password;

    @NotNull
    UserRole role;
}
