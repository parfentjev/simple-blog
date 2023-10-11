package ee.fakeplastictrees.blog.user.controller;

import ee.fakeplastictrees.blog.core.annotation.AuthorizedUserManager;
import ee.fakeplastictrees.blog.user.controller.request.PostUsersRequest;
import ee.fakeplastictrees.blog.user.controller.request.PostUsersTokenRequest;
import ee.fakeplastictrees.blog.user.model.TokenDto;
import ee.fakeplastictrees.blog.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    @AuthorizedUserManager
    public ResponseEntity<TokenDto> createUser(@Valid @RequestBody PostUsersRequest request) {
        TokenDto tokenDto = userService.createUser(request.getUsername(), request.getPassword(), request.getRole());

        return new ResponseEntity<>(tokenDto, HttpStatus.CREATED);
    }

    @PostMapping("/token")
    public ResponseEntity<TokenDto> createToken(@Valid @RequestBody PostUsersTokenRequest request) {
        TokenDto tokenDto = userService.createToken(request.getUsername(), request.getPassword());

        return new ResponseEntity<>(tokenDto, HttpStatus.CREATED);
    }
}
