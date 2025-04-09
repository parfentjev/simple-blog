package ee.fakeplastictrees.blog.service.core.configuration;

import com.auth0.jwt.interfaces.DecodedJWT;
import ee.fakeplastictrees.blog.service.user.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Optional;

public class AuthenticationTokenFilter extends OncePerRequestFilter {
  @Autowired
  private TokenService tokenService;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  @Qualifier("handlerExceptionResolver")
  private HandlerExceptionResolver resolver;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try {
      processAuthHeader(request, response, filterChain);
    } catch (Exception e) {
      resolver.resolveException(request, response, null, e);
    }
  }

  private void processAuthHeader(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    var token = extractToken(request);
    if (token.isPresent()) {
      validateToken(tokenService.parseToken(token.get()), request, response, filterChain);
    }

    filterChain.doFilter(request, response);
  }

  private void validateToken(DecodedJWT token, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    if (tokenService.isExpired(token)) {
      throw new CredentialsExpiredException("Expired JWT.");
    }

    var userDetails = userDetailsService.loadUserByUsername(tokenService.getUsername(token));
    var userAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    userAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(userAuth);
  }

  private Optional<String> extractToken(HttpServletRequest request) {
    var header = request.getHeader("Authorization");
    if (header != null && header.startsWith("Bearer")) {
      return Optional.of(header.substring(7));
    }

    return Optional.empty();
  }
}
