package ee.fakeplastictrees.blog.user.service;

import ee.fakeplastictrees.blog.user.exception.UserServiceException;
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

  public void createUser(String username, String password) throws UserServiceException {
    if (isRegistrationDisabled()) {
      throw new UserServiceException(UserServiceException.REGISTRATION_DISABLED);
    }

    if (userRepository.findByUsername(username).isPresent()) {
      throw new UserServiceException(UserServiceException.USERNAME_ALREADY_TAKEN);
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
