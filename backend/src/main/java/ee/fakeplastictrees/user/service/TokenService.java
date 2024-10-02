package ee.fakeplastictrees.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import ee.fakeplastictrees.user.model.TokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Service
public class TokenService {
  @Value("${token.secret}")
  private String secret;

  @Value("${token.lifespanDays}")
  private Integer lifespanDays;

  @Value("${token.issuer}")
  private String issuer;

  public TokenDto generateToken(String username) {
    var expiresAt = Instant.now().plusSeconds(Duration.ofDays(lifespanDays).toSeconds());

    var token = JWT.create()
      .withIssuer(issuer)
      .withClaim("username", username)
      .withClaim("exp", expiresAt.getEpochSecond())
      .sign(Algorithm.HMAC256(secret));

    return TokenDto.builder()
      .token(token)
      .expiresAt(expiresAt)
      .build();
  }

  public Optional<DecodedJWT> parseToken(String token) {
    var algorithm = Algorithm.HMAC256(secret);
    var verifier = JWT.require(algorithm).withIssuer(issuer).build();
    var decodedToken = verifier.verify(token);

    if (isTokenExpired(decodedToken)) {
      return Optional.empty();
    }

    return Optional.of(decodedToken);
  }

  private boolean isTokenExpired(DecodedJWT decodedToken) {
    return Instant.now().isAfter(Instant.ofEpochSecond(decodedToken.getClaim("exp").asLong()));
  }
}
