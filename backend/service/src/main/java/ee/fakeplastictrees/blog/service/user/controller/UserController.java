package ee.fakeplastictrees.blog.service.user.controller;

import ee.fakeplastictrees.blog.codegen.api.UsersApi;
import ee.fakeplastictrees.blog.codegen.model.TokenDto;
import ee.fakeplastictrees.blog.codegen.model.UsersPostRequest;
import ee.fakeplastictrees.blog.service.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UsersApi {
  @Autowired
  private UserService userService;

  @Override
  public ResponseEntity<TokenDto> usersPost(UsersPostRequest request) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(userService.createUser(request));
  }

  @Override
  public ResponseEntity<TokenDto> usersTokenPost(UsersPostRequest request) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(userService.authenticateUser(request));
  }
}
