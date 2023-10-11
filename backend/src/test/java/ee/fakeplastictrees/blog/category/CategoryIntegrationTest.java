package ee.fakeplastictrees.blog.category;

import ee.fakeplastictrees.blog.category.controller.request.PostCategoriesRequest;
import ee.fakeplastictrees.blog.category.controller.request.PutCategoriesRequest;
import ee.fakeplastictrees.blog.category.model.CategoryDto;
import ee.fakeplastictrees.blog.category.repository.CategoryRepository;
import ee.fakeplastictrees.blog.testsupport.AbstractIntegrationTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.IntStream;

import static ee.fakeplastictrees.blog.core.Utils.builders;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoryIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    public void afterEach() {
        categoryRepository.deleteAll();
    }

    @Test
    public void getCategories() {
        List<CategoryDto> categories = IntStream.range(0, 2).mapToObj(i -> postCategories(postCategoriesRequest())).toList();

        anonymousExecutor().getCategories()
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response).hasSize(categories.size());
                    assertThat(response).containsExactlyInAnyOrderElementsOf(categories);
                });
    }

    @Test
    public void postCategoriesAsAnonymous() {
        anonymousExecutor().postCategories(postCategoriesRequest()).statusCode(401);

        anonymousExecutor().getCategories()
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response).hasSize(0);
                });
    }

    @Test
    public void postCategoriesWithNoBody() {
        editorExecutor().postCategories(null).statusCode(415);

        anonymousExecutor().getCategories()
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response).hasSize(0);
                });
    }

    @Test
    public void postCategoriesWithNullName() {
        editorExecutor().postCategories(builders().category().request().postCategories().build()).statusCode(400);

        anonymousExecutor().getCategories()
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response).hasSize(0);
                });
    }

    @Test
    public void postCategoriesWithEmptyName() {
        PostCategoriesRequest request = postCategoriesRequest();
        request.setName("");
        editorExecutor().postCategories(request).statusCode(400);

        anonymousExecutor().getCategories()
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response).hasSize(0);
                });
    }

    @Test
    public void putCategories() {
        CategoryDto categoryDto = postCategories(postCategoriesRequest());
        categoryDto.setName(RandomStringUtils.randomAlphanumeric(10));

        PutCategoriesRequest request = builders().category().request().putCategories()
                .name(categoryDto.getName())
                .build();

        editorExecutor().putCategoriesById(categoryDto.getId(), request)
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getId()).isEqualTo(categoryDto.getId());
                    assertThat(response.getName()).isEqualTo(categoryDto.getName());
                });

        anonymousExecutor().getCategories()
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response).hasSize(1);
                    assertThat(response).contains(categoryDto);
                });
    }

    @Test
    public void putCategoriesAsAnonymous() {
        CategoryDto categoryDto = postCategories(postCategoriesRequest());

        PutCategoriesRequest request = putCategoriesRequest(categoryDto);
        request.setName(RandomStringUtils.randomAlphanumeric(10));

        anonymousExecutor().putCategoriesById(categoryDto.getId(), request).statusCode(401);

        anonymousExecutor().getCategories()
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response).hasSize(1);
                    assertThat(response).contains(categoryDto);
                });
    }

    @Test
    public void putCategoriesWithNoBody() {
        CategoryDto categoryDto = postCategories(postCategoriesRequest());

        editorExecutor().putCategoriesById(categoryDto.getId(), null).statusCode(400);

        anonymousExecutor().getCategories()
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response).hasSize(1);
                    assertThat(response).contains(categoryDto);
                });
    }

    @Test
    public void putCategoriesWithNullName() {
        CategoryDto categoryDto = postCategories(postCategoriesRequest());

        editorExecutor().putCategoriesById(categoryDto.getId(), builders().category().request().putCategories().build()).statusCode(400);

        anonymousExecutor().getCategories()
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response).hasSize(1);
                    assertThat(response).contains(categoryDto);
                });
    }

    @Test
    public void putCategoriesWithBlankName() {
        CategoryDto categoryDto = postCategories(postCategoriesRequest());

        PutCategoriesRequest request = putCategoriesRequest(categoryDto);
        request.setName(" ");

        editorExecutor().putCategoriesById(categoryDto.getId(), request).statusCode(400);

        anonymousExecutor().getCategories()
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response).hasSize(1);
                    assertThat(response).contains(categoryDto);
                });
    }

    @Test
    public void deleteCategoriesAsAnonymous() {
        CategoryDto categoryDto = postCategories(postCategoriesRequest());
        anonymousExecutor().deleteCategoriesById(categoryDto.getId()).statusCode(401);

        anonymousExecutor().getCategories()
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response).hasSize(1);
                    assertThat(response).contains(categoryDto);
                });
    }

    @Test
    public void deleteCategoriesAsEditor() {
        CategoryDto categoryDto = postCategories(postCategoriesRequest());
        editorExecutor().deleteCategoriesById(categoryDto.getId()).statusCode(204);

        anonymousExecutor().getCategories()
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response).hasSize(0);
                });
    }

    @Test
    public void deleteCategoriesWithoutId() {
        CategoryDto categoryDto = postCategories(postCategoriesRequest());
        editorExecutor().deleteCategoriesById("").statusCode(404);

        anonymousExecutor().getCategories()
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response).hasSize(1);
                    assertThat(response).contains(categoryDto);
                });
    }

    @Test
    public void deleteCategoriesThatDoNotExist() {
        CategoryDto categoryDto = postCategories(postCategoriesRequest());
        editorExecutor().deleteCategoriesById(RandomStringUtils.randomAlphanumeric(10)).statusCode(404);

        anonymousExecutor().getCategories()
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response).hasSize(1);
                    assertThat(response).contains(categoryDto);
                });
    }

    private PostCategoriesRequest postCategoriesRequest() {
        return builders().category().request().postCategories().name(RandomStringUtils.randomAlphanumeric(10)).build();
    }

    private PutCategoriesRequest putCategoriesRequest(CategoryDto categoryDto) {
        return builders().category().request().putCategories()
                .name(categoryDto.getName())
                .build();
    }

    private CategoryDto postCategories(PostCategoriesRequest request) {
        return editorExecutor().postCategories(request).statusCode(201).body();
    }
}
