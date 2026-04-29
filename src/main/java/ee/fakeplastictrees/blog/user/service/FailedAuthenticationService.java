package ee.fakeplastictrees.blog.user.service;

import static java.util.Optional.ofNullable;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FailedAuthenticationService {
  private static LoadingCache<String, Integer> cache;

  private HttpServletRequest request;

  @Value("${user.auth.max.attempts}")
  private Integer maxAttempts;

  @Value("${user.auth.timeout.minutes}")
  private Long timeoutDuration;

  public FailedAuthenticationService(HttpServletRequest request) {
    this.request = request;
  }

  public void registerAuthenticationFailure() {
    var clientIdentifier = getClientIdentifier();
    var authAttempts = getAuthAttempts();
    authAttempts++;

    getCache().put(clientIdentifier, authAttempts);
  }

  public boolean isBlocked() {
    return getAuthAttempts() >= maxAttempts;
  }

  private String getClientIdentifier() {
    var realIp = request.getHeader("X-Real-IP");
    if (realIp != null && !realIp.isBlank()) {
      return realIp;
    }

    return request.getRemoteAddr();
  }

  private LoadingCache<String, Integer> getCache() {
    if (cache == null) {
      cache =
          CacheBuilder.newBuilder()
              .expireAfterWrite(Duration.ofMinutes(timeoutDuration))
              .build(
                  new CacheLoader<>() {
                    @Override
                    public Integer load(String key) {
                      return 0;
                    }
                  });
    }

    return cache;
  }

  private int getAuthAttempts() {
    return ofNullable(getCache().getIfPresent(getClientIdentifier())).orElse(0);
  }
}
