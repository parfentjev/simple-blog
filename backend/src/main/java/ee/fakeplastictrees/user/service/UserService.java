package ee.fakeplastictrees.user.service;

import ee.fakeplastictrees.user.exception.UserRegistrationDisabledException;
import ee.fakeplastictrees.user.exception.UsernameAlreadyTakenException;
import ee.fakeplastictrees.user.model.TokenDto;
import ee.fakeplastictrees.user.model.UserAuthDto;
import ee.fakeplastictrees.user.repository.UserRepository;
import ee.fakeplastictrees.user.util.UserMapper;
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

  public TokenDto createUser(UserAuthDto userAuthDto) {
    if (registrationDisabled) {
      throw new UserRegistrationDisabledException();
    }

    if (userRepository.findByUsername(userAuthDto.getUsername()).isPresent()) {
      throw new UsernameAlreadyTakenException();
    }

    var user = UserMapper.authDtoToUser(userAuthDto);
    user.setPassword(passwordEncoder.encode(userAuthDto.getPassword()));
    user.setCreatedAt(Instant.now());
    user.setActive(true);
    userRepository.save(user);

    return tokenService.generateToken(userAuthDto.getUsername());
  }

  public TokenDto authenticateUser(UserAuthDto userAuthDto) {
    var authentication = new UsernamePasswordAuthenticationToken(userAuthDto.getUsername(), userAuthDto.getPassword());
    authenticationManager.authenticate(authentication);

    return tokenService.generateToken(userAuthDto.getUsername());
  }
}
