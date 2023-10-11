package ee.fakeplastictrees.blog.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import ee.fakeplastictrees.blog.testsupport.AbstractIntegrationTest;
import ee.fakeplastictrees.blog.testsupport.ApiExecutor;
import ee.fakeplastictrees.blog.testsupport.TestSupport;
import ee.fakeplastictrees.blog.user.controller.request.PostUsersRequest;
import ee.fakeplastictrees.blog.user.controller.request.PostUsersTokenRequest;
import ee.fakeplastictrees.blog.user.model.UserRole;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;

import static ee.fakeplastictrees.blog.core.Utils.builders;
import static org.assertj.core.api.Assertions.assertThat;

// todo: do as in CategoryIntegrationTest
public class UserIntegrationTest extends AbstractIntegrationTest {
    @Value("${token.secret}")
    private String tokenSecret;

    @Value("${token.lifespan}")
    private Long tokenLifespan;

    @Value("${token.issuer}")
    private String tokenIssuer;

    @Test
    public void postUsers() {
        ApiExecutor apiExecutor = TestSupport.apiExecutor(baseUrl());

        PostUsersRequest request = builders().user().request().postUsers()
                .username(RandomStringUtils.randomAlphanumeric(10))
                .password(RandomStringUtils.randomAlphanumeric(10))
                .role(UserRole.EDITOR)
                .build();

        apiExecutor.postUsers(request)
                .statusCode(401); // todo: execute with ADMIN role and verify the token
    }

    @Test
    public void postUsersToken() {
        ApiExecutor apiExecutor = TestSupport.apiExecutor(baseUrl());

        PostUsersTokenRequest request = builders().user().request().postUsersToken()
                .username("editor")
                .password("test")
                .build();

        Instant targetExpirationDate = Instant.now().plusMillis(tokenLifespan);
        long minExpirationDate = targetExpirationDate.minusSeconds(10).toEpochMilli();
        long maxExpirationDate = targetExpirationDate.plusSeconds(10).toEpochMilli();

        apiExecutor.postUsersToken(request)
                .statusCode(201)
                .responseConsumer(response -> {
                    Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
                    JWTVerifier verifier = JWT.require(algorithm).withIssuer(tokenIssuer).build();
                    DecodedJWT decodedJWT = verifier.verify(response.getToken());

                    assertThat(decodedJWT.getClaim("iss").asString()).isEqualTo(tokenIssuer);
                    assertThat(decodedJWT.getClaim("username").asString()).isEqualTo("editor");
                    Long expirationDate = decodedJWT.getClaim("expirationDate").asLong();
                    assertThat(expirationDate).isBetween(minExpirationDate, maxExpirationDate);
                    assertThat(response.getExpirationDate()).isEqualTo(expirationDate);
                });
    }
}
