package ee.fakeplastictrees.user.controller;

import ee.fakeplastictrees.user.model.TokenDto;
import ee.fakeplastictrees.user.model.UserAuthDto;
import ee.fakeplastictrees.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TokenDto> createUser(@Valid @RequestBody UserAuthDto request) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(userService.createUser(request));
  }

  @PostMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TokenDto> createToken(@Valid @RequestBody UserAuthDto request) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(userService.authenticateUser(request));
  }
}
