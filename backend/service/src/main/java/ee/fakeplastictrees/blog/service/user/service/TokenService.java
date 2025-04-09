package ee.fakeplastictrees.blog.service.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import ee.fakeplastictrees.blog.codegen.model.TokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class TokenService {
  @Value("${token.secret}")
  private String secret;

  @Value("${token.lifespan.days}")
  private Integer lifespanDays;

  @Value("${token.issuer}")
  private String issuer;

  private static final String CLAIM_USERNAME = "username";
  private static final String CLAIM_EXPIRES = "exp";

  public TokenDto generateToken(String username) {
    var expiresAt = Instant.now().plusSeconds(Duration.ofDays(lifespanDays).toSeconds());

    var token = JWT.create()
      .withIssuer(issuer)
      .withClaim(CLAIM_USERNAME, username)
      .withClaim(CLAIM_EXPIRES, expiresAt.getEpochSecond())
      .sign(Algorithm.HMAC256(secret));

    return new TokenDto()
      .token(token)
      .expires(expiresAt.toEpochMilli());
  }

  public DecodedJWT parseToken(String token) {
    var algorithm = Algorithm.HMAC256(secret);
    var verifier = JWT.require(algorithm).withIssuer(issuer).build();
    return verifier.verify(token);
  }

  public boolean isExpired(DecodedJWT decodedToken) {
    return Instant.now().isAfter(Instant.ofEpochSecond(decodedToken.getClaim(CLAIM_EXPIRES).asLong()));
  }

  public String getUsername(DecodedJWT token) {
    return token.getClaim(CLAIM_USERNAME).asString();
  }
}
