package ee.fakeplastictrees.blog.user.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    String id;

    String username;

    String password;

    UserRole role;
}
