package ee.fakeplastictrees.blog.service.user.service;

import ee.fakeplastictrees.blog.codegen.model.TokenDto;
import ee.fakeplastictrees.blog.codegen.model.UsersPostRequest;
import ee.fakeplastictrees.blog.service.user.model.UserExceptionFactory;
import ee.fakeplastictrees.blog.service.user.model.UserRepository;
import ee.fakeplastictrees.blog.service.user.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Value("${registration.disabled}")
  private boolean registrationDisabled;

  public TokenDto createUser(UsersPostRequest request) {
    if (registrationDisabled) {
      throw UserExceptionFactory.registrationDisabled();
    }

    if (userRepository.findByUsername(request.getUsername()).isPresent()) {
      throw UserExceptionFactory.usernameAlreadyTaken();
    }

    var user = UserMapper.requestToUser(request);
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setCreatedAt(Instant.now());
    user.setActive(true);
    userRepository.save(user);

    return tokenService.generateToken(request.getUsername());
  }

  public TokenDto authenticateUser(UsersPostRequest request) {
    var authentication = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
    authenticationManager.authenticate(authentication);

    return tokenService.generateToken(request.getUsername());
  }
}
