package ee.fakeplastictrees.blog.service.user.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class FailedAuthenticationListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
  @Autowired
  private HttpServletRequest request;

  @Autowired
  private FailedAuthenticationService failedAuthenticationService;

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
