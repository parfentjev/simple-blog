package ee.fakeplastictrees.blog.user.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class FailedAuthenticationListener
    implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
  private final HttpServletRequest request;

  private final FailedAuthenticationService failedAuthenticationService;

  public FailedAuthenticationListener(
      HttpServletRequest request, FailedAuthenticationService failedAuthenticationService) {
    this.request = request;
    this.failedAuthenticationService = failedAuthenticationService;
  }

  @Override
  public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
    final var xfHeader = request.getHeader("X-Forwarded-For");

    if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr())) {
      failedAuthenticationService.consume(request.getRemoteAddr());
    } else {
      failedAuthenticationService.consume(xfHeader.split(",")[0]);
    }
  }
}
