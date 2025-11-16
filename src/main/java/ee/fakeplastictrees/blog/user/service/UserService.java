package ee.fakeplastictrees.blog.user.service;

import ee.fakeplastictrees.blog.user.exception.CreateUserException;
import ee.fakeplastictrees.blog.user.model.User;
import ee.fakeplastictrees.blog.user.repository.UserRepository;
import java.time.Instant;
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

  public void createUser(String username, String password) throws CreateUserException {
    if (isRegistrationDisabled()) {
      throw new CreateUserException("Registration is disabled.");
    }

    if (userRepository.findByUsername(username).isPresent()) {
      throw new CreateUserException("Username is already taken.");
    }

    var user = new User();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(password));
    user.setCreatedAt(Instant.now());
    user.setActive(true);
    userRepository.save(user);
  }

  public boolean isRegistrationDisabled() {
    return registrationDisabled;
  }
}
