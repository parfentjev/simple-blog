package ee.fakeplastictrees.blog.user.service;

import static java.util.Optional.ofNullable;
import static java.util.concurrent.TimeUnit.MINUTES;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FailedAuthenticationService {
  private static LoadingCache<String, Integer> cache;
  private final HttpServletRequest request;

  @Value("${user.auth.max.attempts}")
  private Integer maxAttempts;

  @Value("${user.auth.timeout.minutes}")
  private Long timeoutDuration;

  public FailedAuthenticationService(HttpServletRequest request) {
    this.request = request;
  }

  private LoadingCache<String, Integer> getCache() {
    if (cache == null) {
      cache =
          CacheBuilder.newBuilder()
              .expireAfterWrite(timeoutDuration, MINUTES)
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

  public void consume(String clientIdentifier) {
    var authAttempts = ofNullable(getCache().getIfPresent(clientIdentifier)).orElse(0);
    authAttempts++;

    getCache().put(clientIdentifier, authAttempts);
  }

  public boolean isBlocked() {
    return ofNullable(getCache().getIfPresent(getKey())).orElse(0) >= maxAttempts;
  }

  private String getKey() {
    final var xfHeader = request.getHeader("X-Forwarded-For");
    if (xfHeader != null) {
      return xfHeader.split(",")[0];
    }

    return request.getRemoteAddr();
  }
}
