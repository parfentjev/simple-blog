package ee.fakeplastictrees.blog.user.controller.request;

import ee.fakeplastictrees.blog.user.model.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostUsersRequest {
    @NotBlank
    String username;

    @NotBlank
    String password;

    @NotNull
    UserRole role;
}
