package ee.fakeplastictrees.core.configuration;

import com.auth0.jwt.exceptions.JWTVerificationException;
import ee.fakeplastictrees.user.service.TokenService;
import ee.fakeplastictrees.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Optional;

public class AuthenticationTokenFilter extends OncePerRequestFilter {
  @Autowired
  private UserService userService;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  @Qualifier("handlerExceptionResolver")
  private HandlerExceptionResolver resolver;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    var token = getTokenFromRequest(request);

    if (token.isPresent()) {
      try {
        var decodedToken = tokenService.parseToken(token.get()).orElseThrow(() -> new JWTVerificationException("invalid token"));
        var username = Optional.ofNullable(decodedToken.getClaim("username")).orElseThrow(() -> new JWTVerificationException("missing username"));
        var userDetails = userDetailsService.loadUserByUsername(username.asString());
        var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        chain.doFilter(request, response);
      } catch (Exception e) {
        resolver.resolveException(request, response, null, e);
      }
    } else {
      chain.doFilter(request, response);
    }
  }

  private Optional<String> getTokenFromRequest(HttpServletRequest request) {
    var authorizationHeader = request.getHeader("Authorization");

    if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
      return Optional.of(authorizationHeader.substring(7));
    }

    return Optional.empty();
  }
}
