package ee.fakeplastictrees.blog.user;

import ee.fakeplastictrees.blog.user.controller.request.UserRequestBuilders;
import ee.fakeplastictrees.blog.user.model.TokenDto;
import ee.fakeplastictrees.blog.user.model.User;
import ee.fakeplastictrees.blog.user.model.UserDto;

public class UserBuilders {
    public UserDto.UserDtoBuilder userDto() {
        return UserDto.builder();
    }

    public TokenDto.TokenDtoBuilder tokenDto() {
        return TokenDto.builder();
    }

    public User.UserBuilder user() {
        return User.builder();
    }

    public UserRequestBuilders request() {
        return new UserRequestBuilders();
    }
}
