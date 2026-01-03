package ee.fakeplastictrees.blog.user.service;

import ee.fakeplastictrees.blog.core.exception.HTTPNotFoundException;
import ee.fakeplastictrees.blog.user.model.UserRole;
import ee.fakeplastictrees.blog.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ExtendedUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  private final FailedAuthenticationService failedAuthenticationService;

  public ExtendedUserDetailsService(
      UserRepository userRepository, FailedAuthenticationService failedAuthenticationService) {
    this.userRepository = userRepository;
    this.failedAuthenticationService = failedAuthenticationService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (failedAuthenticationService.isBlocked()) {
      throw new HTTPNotFoundException();
    }

    var user = userRepository.findByUsername(username);
    if (user.isEmpty() || !user.get().getActive()) {
      throw new HTTPNotFoundException();
    }

    return org.springframework.security.core.userdetails.User.withUsername(user.get().getUsername())
        .password(user.get().getPassword())
        .roles(new String[] {UserRole.ADMIN.name()})
        .build();
  }
}
