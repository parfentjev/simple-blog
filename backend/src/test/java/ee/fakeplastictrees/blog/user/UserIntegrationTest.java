package ee.fakeplastictrees.blog.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ee.fakeplastictrees.blog.testsupport.AbstractIntegrationTest;
import ee.fakeplastictrees.blog.testsupport.TestCredentials;
import ee.fakeplastictrees.blog.user.controller.request.PostUsersRequest;
import ee.fakeplastictrees.blog.user.controller.request.PostUsersTokenRequest;
import ee.fakeplastictrees.blog.user.model.UserRole;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;

import static ee.fakeplastictrees.blog.core.Utils.builders;
import static org.assertj.core.api.Assertions.assertThat;

public class UserIntegrationTest extends AbstractIntegrationTest {
    @Value("${token.secret}")
    private String tokenSecret;

    @Value("${token.lifespan}")
    private Long tokenLifespan;

    @Value("${token.issuer}")
    private String tokenIssuer;

    @Test
    public void postUsers() {
        PostUsersRequest request = postUsersRequest();

        adminExecutor().postUsers(request)
                .statusCode(201)
                .responseConsumer(response -> {
                    assertThat(response.getUsername()).isEqualTo(request.getUsername());
                    assertThat(response.getRole()).isEqualTo(request.getRole());
                });
    }

    @Test
    public void postUsersAsAnonymous() {
        anonymousExecutor().postUsers(postUsersRequest()).statusCode(401);
    }

    @Test
    public void postUsersAsEditor() {
        editorExecutor().postUsers(postUsersRequest()).statusCode(401);
    }

    @Test
    public void postUsersWithNoBody() {
        adminExecutor().postUsers(null).statusCode(415);
    }

    @Test
    public void postUsersWithNullUsername() {
        PostUsersRequest request = postUsersRequest();
        request.setUsername(null);

        adminExecutor().postUsers(request).statusCode(400);
    }

    @Test
    public void postUsersWithBlankUsername() {
        PostUsersRequest request = postUsersRequest();
        request.setUsername(" ");

        adminExecutor().postUsers(request).statusCode(400);
    }

    @Test
    public void postUsersWithNullPassword() {
        PostUsersRequest request = postUsersRequest();
        request.setPassword(null);

        adminExecutor().postUsers(request).statusCode(400);
    }

    @Test
    public void postUsersWithBlankPassword() {
        PostUsersRequest request = postUsersRequest();
        request.setPassword(" ");

        adminExecutor().postUsers(request).statusCode(400);
    }

    @Test
    public void postUsersWithNullRole() {
        PostUsersRequest request = postUsersRequest();
        request.setRole(null);

        adminExecutor().postUsers(request).statusCode(400);
    }

    @Test
    public void postUsersWithNotExistingRole() {
        PostUsersRequest request = postUsersRequest();
        ObjectNode objectNode = new ObjectMapper().valueToTree(request);
        objectNode.put("role", "NOT_EXISTING_ROLE");

        adminExecutor()
                .requestBuilder(r -> r.body(objectNode).contentType(ContentType.JSON).post("/users"), Void.class)
                .statusCode(400)
                .message("Invalid request body.");
    }

    @Test
    public void postUsersToken() {
        PostUsersTokenRequest request = postUsersTokenRequest();
        Instant targetExpirationDate = Instant.now().plusMillis(tokenLifespan);
        long minExpirationDate = targetExpirationDate.minusSeconds(10).toEpochMilli();
        long maxExpirationDate = targetExpirationDate.plusSeconds(10).toEpochMilli();

        anonymousExecutor().postUsersToken(request)
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

    @Test
    public void postUsersTokenInvalidUsername() {
        PostUsersTokenRequest request = postUsersTokenRequest();
        request.setUsername(RandomStringUtils.randomAlphanumeric(10));

        anonymousExecutor().postUsersToken(request).statusCode(401);
    }

    @Test
    public void postUsersTokenInvalidPassword() {
        PostUsersTokenRequest request = postUsersTokenRequest();
        request.setPassword(RandomStringUtils.randomAlphanumeric(10));

        anonymousExecutor().postUsersToken(request).statusCode(401);
    }

    private PostUsersRequest postUsersRequest() {
        return builders().user().request().postUsers()
                .username(RandomStringUtils.randomAlphanumeric(10))
                .password(RandomStringUtils.randomAlphanumeric(10))
                .role(UserRole.EDITOR)
                .build();
    }

    private PostUsersTokenRequest postUsersTokenRequest() {
        return builders().user().request().postUsersToken()
                .username(TestCredentials.EDITOR.getUsername())
                .password(TestCredentials.EDITOR.getPassword())
                .build();
    }
}
