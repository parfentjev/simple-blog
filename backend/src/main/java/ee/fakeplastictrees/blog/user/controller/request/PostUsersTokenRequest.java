package ee.fakeplastictrees.blog.user.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostUsersTokenRequest {
    @NotBlank
    String username;

    @NotBlank
    String password;
}
