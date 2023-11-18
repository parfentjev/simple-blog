package ee.fakeplastictrees.blog.testsupport;


import ee.fakeplastictrees.blog.user.controller.request.PostUsersTokenRequest;
import ee.fakeplastictrees.blog.user.model.TokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static ee.fakeplastictrees.blog.core.Utils.builders;

@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {
    @Value("${test.integration.baseUrl}")
    private String baseUrl;

    @LocalServerPort
    private int serverPort;

    private ApiExecutor anonymousExecutor;
    private ApiExecutor editorExecutor;
    private ApiExecutor adminExecutor;

    protected String baseUrl() {
        return baseUrl + ":" + serverPort;
    }

    protected ApiExecutor anonymousExecutor() {
        if (anonymousExecutor == null) {
            anonymousExecutor = TestSupport.apiExecutor(baseUrl());
        }

        return anonymousExecutor;
    }

    protected ApiExecutor editorExecutor() {
        if (editorExecutor == null) {
            editorExecutor = TestSupport.apiExecutor(baseUrl(), postUsersToken(postUsersTokenRequest(TestCredentials.EDITOR)));
        }

        return editorExecutor;
    }

    protected ApiExecutor adminExecutor() {
        if (adminExecutor == null) {
            adminExecutor = TestSupport.apiExecutor(baseUrl(), postUsersToken(postUsersTokenRequest(TestCredentials.ADMIN)));
        }

        return adminExecutor;
    }

    protected PostUsersTokenRequest postUsersTokenRequest(TestCredentials credentials) {
        return builders().user().request().postUsersToken()
                .username(credentials.getUsername())
                .password(credentials.getPassword())
                .build();
    }

    private String postUsersToken(PostUsersTokenRequest request) {
        return anonymousExecutor().postUsersToken(request)
                .statusCode(201)
                .responseConsumer(TokenDto::getToken);
    }
}
