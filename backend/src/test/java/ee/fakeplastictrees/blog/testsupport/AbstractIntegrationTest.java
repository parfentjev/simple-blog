package ee.fakeplastictrees.blog.testsupport;


import ee.fakeplastictrees.blog.user.controller.request.PostUsersTokenRequest;
import ee.fakeplastictrees.blog.user.model.TokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.io.IOException;

import static ee.fakeplastictrees.blog.core.Utils.builders;

@Testcontainers
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

//    @Container
//    @ServiceConnection
//    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest")
//            .withCopyFileToContainer(MountableFile.forClasspathResource("/dump"), "/dump");
//
//    protected AbstractIntegrationTest() {
//        try {
//            mongoDBContainer.execInContainer("mongorestore", "/dump");
//        } catch (IOException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }

    static final MongoDBContainer mongoDBContainer;

    static {
        try {
            mongoDBContainer = new MongoDBContainer("mongo:latest");
            mongoDBContainer.start();
            mongoDBContainer.copyFileToContainer(MountableFile.forClasspathResource("/dump"), "/dump");
            mongoDBContainer.execInContainer("mongorestore", "/dump");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @DynamicPropertySource
    static void initialize(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

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
