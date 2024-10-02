package ee.fakeplastictrees.user.util;

import ee.fakeplastictrees.user.model.User;
import ee.fakeplastictrees.user.model.UserAuthDto;
import ee.fakeplastictrees.user.model.UserDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
  public User dtoToUser(UserDto userDto) {
    return null;
  }

  public User authDtoToUser(UserAuthDto userAuthDto) {
    return User.builder()
      .username(userAuthDto.getUsername())
      .password(userAuthDto.getPassword())
      .build();
  }

  public UserDto userToDto(User user) {
    return UserDto.builder()
      .id(user.getId())
      .username(user.getUsername())
      .createdAt(user.getCreatedAt())
      .active(user.getActive())
      .build();
  }
}
