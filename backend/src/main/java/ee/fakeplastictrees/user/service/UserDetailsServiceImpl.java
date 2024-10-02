package ee.fakeplastictrees.user.service;

import ee.fakeplastictrees.user.exception.UserNotFoundException;
import ee.fakeplastictrees.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = userRepository.findByUsername(username);

    if (user.isEmpty() || !user.get().getActive()) {
      throw new UserNotFoundException();
    }

    return org.springframework.security.core.userdetails.User
      .withUsername(user.get().getUsername())
      .password(user.get().getPassword())
      .roles(new String[]{"POST_EDITOR"})
      .build();
  }
}