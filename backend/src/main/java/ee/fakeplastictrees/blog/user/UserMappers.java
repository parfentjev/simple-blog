package ee.fakeplastictrees.blog.user;

import ee.fakeplastictrees.blog.user.model.User;
import ee.fakeplastictrees.blog.user.model.UserDto;

import static ee.fakeplastictrees.blog.core.Utils.builders;

public class UserMappers {
    public UserDto userToUserDto(User user) {
        return builders().user().userDto()
                .username(user.getUsername())
                .build();
    }
}
