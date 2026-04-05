package ee.fakeplastictrees.blog.user.service;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class FailedAuthenticationListener
    implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
  private final FailedAuthenticationService failedAuthenticationService;

  public FailedAuthenticationListener(FailedAuthenticationService failedAuthenticationService) {
    this.failedAuthenticationService = failedAuthenticationService;
  }

  @Override
  public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
    failedAuthenticationService.registerAuthenticationFailure();
  }
}
