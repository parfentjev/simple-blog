package ee.fakeplastictrees.blog.service.user.service;

import ee.fakeplastictrees.blog.service.user.model.UserExceptionFactory;
import ee.fakeplastictrees.blog.service.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ExtendedUserDetailsService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = userRepository.findByUsername(username);

    if (user.isEmpty() || !user.get().getActive()) {
      throw UserExceptionFactory.notFound();
    }

    return org.springframework.security.core.userdetails.User
      .withUsername(user.get().getUsername())
      .password(user.get().getPassword())
      .roles(new String[]{"POST_EDITOR"})
      .build();
  }
}