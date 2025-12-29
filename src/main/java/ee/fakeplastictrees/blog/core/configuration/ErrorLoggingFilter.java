package ee.fakeplastictrees.blog.core.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class ErrorLoggingFilter extends OncePerRequestFilter {
  private static final Logger logger = LoggerFactory.getLogger(ErrorLoggingFilter.class);

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    try {
      filterChain.doFilter(request, response);
    } catch (Exception e) {
      log(Level.ERROR, "uncaught exception", null, request);
      throw e;
    }

    var status = response.getStatus();
    if (status < 400) {
      return;
    } else if (status >= 500) {
      log(Level.ERROR, "server error", status, request);
    } else if (status != 404) {
      log(Level.WARN, "client error", status, request);
    }
  }

  private void log(Level level, String description, Integer status, HttpServletRequest request) {
    logger
        .atLevel(level)
        .log(
            "{}: status '{}'; uri: '{} {}'; ip: '{}'; user-agent: '{}'; query: '{}'",
            description,
            status,
            request.getMethod(),
            request.getRequestURI(),
            getIp(request),
            request.getHeader("User-Agent"),
            request.getQueryString());
  }

  private String getIp(HttpServletRequest request) {
    var header = request.getHeader("X-Forwarded-For");
    if (header == null) {
      return request.getRemoteAddr();
    }

    return header.split(",")[0];
  }
}
