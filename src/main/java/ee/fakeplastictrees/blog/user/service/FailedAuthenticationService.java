package ee.fakeplastictrees.blog.user.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class FailedAuthenticationService {
  @Value("${user.auth.max.attempts}")
  private Integer maxAttempts;

  @Value("${user.auth.timeout.minutes}")
  private Long timeoutDuration;

  private static LoadingCache<String, Integer> cache;

  private final HttpServletRequest request;

  public FailedAuthenticationService(HttpServletRequest request) {
    this.request = request;
  }

  private LoadingCache<String, Integer> getCache() {
    if (cache == null) {
      cache = CacheBuilder.newBuilder().expireAfterWrite(timeoutDuration, TimeUnit.MINUTES).build(new CacheLoader<>() {
        @Override
        public Integer load(String key) {
          return 0;
        }
      });
    }

    return cache;
  }

  public void consume(String key) {
    var attempts = new AtomicInteger(0);
    Optional.ofNullable(getCache().getIfPresent(key)).ifPresent(attempts::set);
    getCache().put(key, attempts.incrementAndGet());
  }

  public boolean isBlocked() {
    return Optional.ofNullable(getCache().getIfPresent(getKey())).orElse(0) >= maxAttempts;
  }

  private String getKey() {
    final var xfHeader = request.getHeader("X-Forwarded-For");
    if (xfHeader != null) {
      return xfHeader.split(",")[0];
    }

    return request.getRemoteAddr();
  }
}
