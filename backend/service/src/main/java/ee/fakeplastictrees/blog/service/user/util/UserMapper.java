package ee.fakeplastictrees.blog.service.user.util;

import ee.fakeplastictrees.blog.codegen.model.UsersPostRequest;
import ee.fakeplastictrees.blog.service.user.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
  public User requestToUser(UsersPostRequest request) {
    return User.builder()
      .username(request.getUsername())
      .password(request.getPassword())
      .build();
  }
}
