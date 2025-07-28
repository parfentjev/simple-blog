package ee.fakeplastictrees.blog.user.service;

import ee.fakeplastictrees.blog.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  @Value("${user.registration.disabled}")
  private boolean registrationDisabled;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }
}
