package ee.fakeplastictrees.blog.post;

import ee.fakeplastictrees.blog.category.controller.request.PostCategoriesRequest;
import ee.fakeplastictrees.blog.category.model.CategoryDto;
import ee.fakeplastictrees.blog.category.repository.CategoryRepository;
import ee.fakeplastictrees.blog.post.controller.request.PostPostsRequest;
import ee.fakeplastictrees.blog.post.controller.request.PutPostsRequest;
import ee.fakeplastictrees.blog.post.model.PostDto;
import ee.fakeplastictrees.blog.post.model.PostPreviewDto;
import ee.fakeplastictrees.blog.post.repository.PostRepository;
import ee.fakeplastictrees.blog.testsupport.AbstractIntegrationTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static ee.fakeplastictrees.blog.core.Utils.builders;
import static ee.fakeplastictrees.blog.core.Utils.mappers;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class PostIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private static final List<CategoryDto> categories;

    static {
        categories = List.of(
                builders().category().categoryDto()
                        .id("1")
                        .name("Category1")
                        .build(),
                builders().category().categoryDto()
                        .id("2")
                        .name("Category2")
                        .build()
        );
    }

    @AfterEach
    public void afterEach() {
        postRepository.deleteAll();

        categoryRepository.findAll()
                .stream()
                .filter(c -> !NumberUtils.isCreatable(c.getId()))
                .forEach(c -> categoryRepository.delete(c));
    }

    @Test
    public void getPosts() {
        Instant instantNow = Instant.now();

        List<PostPreviewDto> posts = IntStream.range(0, 3)
                .mapToObj(i -> {
                    PostPostsRequest request = postPostsRequest();
                    request.setDate(instantNow.minusSeconds(i).toString());
                    PostDto postDto = editorExecutor().postPosts(request).statusCode(201).body();

                    return mappers().post().postDtoToPostPreviewDto(postDto);
                })
                .toList();

        anonymousExecutor().getPosts(null, null)
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getPage()).isEqualTo(1);
                    assertThat(response.getTotalPages()).isEqualTo(1);
                    assertThat(response.getItems()).containsExactlyElementsOf(posts);
                });

        anonymousExecutor().getPosts(1, 2)
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getPage()).isEqualTo(1);
                    assertThat(response.getTotalPages()).isEqualTo(2);
                    assertThat(response.getItems()).containsExactlyElementsOf(posts.subList(0, 2));
                });

        anonymousExecutor().getPosts(2, 2)
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getPage()).isEqualTo(2);
                    assertThat(response.getTotalPages()).isEqualTo(2);
                    assertThat(response.getItems()).containsExactlyElementsOf(posts.subList(2, 3));
                });
    }

    @Test
    public void getPostsFilterByCategory() {
        List<CategoryDto> newCategories = List.of(
                generateCategoryDto("3", "Category3"),
                generateCategoryDto("4", "Category4")
        );

        List<PostPostsRequest> postPostsRequests = IntStream.range(0, 2).mapToObj(i -> postPostsRequest()).toList();
        postPostsRequests.get(0).setCategories(List.of(
                categories.get(0),
                newCategories.get(0))
        );
        postPostsRequests.get(1).setCategories(List.of(
                categories.get(0),
                newCategories.get(1))
        );

        List<PostPreviewDto> posts = postPostsRequests.stream()
                .map(request -> {
                    PostDto postDto = editorExecutor().postPosts(request).statusCode(201).body();

                    return mappers().post().postDtoToPostPreviewDto(postDto);
                })
                .toList();

        anonymousExecutor().getPosts(1, 2, "1")
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getItems()).extracting(PostPreviewDto::getId).containsExactlyInAnyOrder(posts.get(0).getId(), posts.get(1).getId());
                });

        anonymousExecutor().getPosts(1, 2, newCategories.get(0).getId())
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getItems()).extracting(PostPreviewDto::getId).containsExactlyInAnyOrder(posts.get(0).getId());
                });
    }

    @Test
    public void getPostsEmptyList() {
        anonymousExecutor().getPosts(null, null)
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getPage()).isEqualTo(1);
                    assertThat(response.getTotalPages()).isEqualTo(0);
                    assertThat(response.getItems()).isEmpty();
                });
    }

    @Test
    public void getPostsWithNotVisiblePost() {
        PostPostsRequest visiblePostRequest = postPostsRequest();
        PostPostsRequest notVisiblePostRequest = postPostsRequest();
        notVisiblePostRequest.setVisible(false);

        List<PostPreviewDto> visiblePosts = Stream.of(visiblePostRequest, notVisiblePostRequest)
                .map(request -> editorExecutor().postPosts(request).statusCode(201).body())
                .filter(PostDto::getVisible)
                .map(postDto -> mappers().post().postDtoToPostPreviewDto(postDto))
                .toList();

        anonymousExecutor().getPosts(null, null)
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getPage()).isEqualTo(1);
                    assertThat(response.getTotalPages()).isEqualTo(1);
                    assertThat(response.getItems()).hasSize(1);
                    assertThat(response.getItems()).containsExactlyElementsOf(visiblePosts);
                });
    }

    @Test
    public void getPostsById() {
        PostDto postDto = postPosts(postPostsRequest());

        anonymousExecutor().getPostsById(postDto.getId())
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getId()).isEqualTo(postDto.getId());
                    assertThat(response.getTitle()).isEqualTo(postDto.getTitle());
                    assertThat(response.getSummary()).isEqualTo(postDto.getSummary());
                    assertThat(response.getText()).isEqualTo(postDto.getText());
                    assertThat(response.getDate()).isEqualTo(postDto.getDate());
                    assertThat(response.getVisible()).isEqualTo(postDto.getVisible());
                    assertThat(response.getCategories()).containsExactlyInAnyOrderElementsOf(postDto.getCategories());
                });
    }

    @Test
    public void getPostsByIdNotVisiblePostAsAnonymous() {
        PostPostsRequest request = postPostsRequest();
        request.setVisible(false);
        PostDto postDto = postPosts(request);

        anonymousExecutor().getPostsById(postDto.getId())
                .statusCode(404);
    }

    @Test
    public void getPostsByIdNotVisiblePostAsEditor() {
        PostPostsRequest request = postPostsRequest();
        request.setVisible(false);
        PostDto postDto = postPosts(request);

        editorExecutor().getPostsById(postDto.getId())
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getId()).isEqualTo(postDto.getId());
                    assertThat(response.getTitle()).isEqualTo(postDto.getTitle());
                    assertThat(response.getSummary()).isEqualTo(postDto.getSummary());
                    assertThat(response.getText()).isEqualTo(postDto.getText());
                    assertThat(response.getDate()).isEqualTo(postDto.getDate());
                    assertThat(response.getVisible()).isEqualTo(postDto.getVisible());
                    assertThat(response.getCategories()).containsExactlyInAnyOrderElementsOf(postDto.getCategories());
                });
    }

    @Test
    public void postPostsAsAnonymous() {
        anonymousExecutor().postPosts(postPostsRequest()).statusCode(401);

        anonymousExecutor().getPosts(null, null)
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getPage()).isEqualTo(1);
                    assertThat(response.getTotalPages()).isEqualTo(0);
                    assertThat(response.getItems()).isEmpty();
                });
    }

    @Test
    public void postPostsWithNoBody() {
        editorExecutor().postPosts(null).statusCode(415);

        anonymousExecutor().getPosts(null, null)
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getPage()).isEqualTo(1);
                    assertThat(response.getTotalPages()).isEqualTo(0);
                    assertThat(response.getItems()).isEmpty();
                });
    }

    @Test
    public void postPostsWithNullTitle() {
        PostPostsRequest request = postPostsRequest();
        request.setTitle(null);

        editorExecutor().postPosts(request).statusCode(400);

        anonymousExecutor().getPosts(null, null)
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getPage()).isEqualTo(1);
                    assertThat(response.getTotalPages()).isEqualTo(0);
                    assertThat(response.getItems()).isEmpty();
                });
    }

    @Test
    public void postPostsWithBlankTitle() {
        PostPostsRequest request = postPostsRequest();
        request.setTitle(" ");

        editorExecutor().postPosts(request).statusCode(400);

        anonymousExecutor().getPosts(null, null)
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getPage()).isEqualTo(1);
                    assertThat(response.getTotalPages()).isEqualTo(0);
                    assertThat(response.getItems()).isEmpty();
                });
    }

    @Test
    public void postPostsWithNullSummary() {
        PostPostsRequest request = postPostsRequest();
        request.setSummary(null);

        editorExecutor().postPosts(request).statusCode(400);

        anonymousExecutor().getPosts(null, null)
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getPage()).isEqualTo(1);
                    assertThat(response.getTotalPages()).isEqualTo(0);
                    assertThat(response.getItems()).isEmpty();
                });
    }

    @Test
    public void postPostsWithBlankSummary() {
        PostPostsRequest request = postPostsRequest();
        request.setSummary(" ");

        editorExecutor().postPosts(request).statusCode(400);

        anonymousExecutor().getPosts(null, null)
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getPage()).isEqualTo(1);
                    assertThat(response.getTotalPages()).isEqualTo(0);
                    assertThat(response.getItems()).isEmpty();
                });
    }

    @Test
    public void postPostsWithNullText() {
        PostPostsRequest request = postPostsRequest();
        request.setText(null);

        editorExecutor().postPosts(request).statusCode(400);

        anonymousExecutor().getPosts(null, null)
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getPage()).isEqualTo(1);
                    assertThat(response.getTotalPages()).isEqualTo(0);
                    assertThat(response.getItems()).isEmpty();
                });
    }

    @Test
    public void postPostsWithBlankText() {
        PostPostsRequest request = postPostsRequest();
        request.setText(" ");

        editorExecutor().postPosts(request).statusCode(400);

        anonymousExecutor().getPosts(null, null)
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getPage()).isEqualTo(1);
                    assertThat(response.getTotalPages()).isEqualTo(0);
                    assertThat(response.getItems()).isEmpty();
                });
    }

    @Test
    public void postPostsWithNullDate() {
        PostPostsRequest request = postPostsRequest();
        request.setDate(null);

        editorExecutor().postPosts(request).statusCode(400);

        anonymousExecutor().getPosts(null, null)
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getPage()).isEqualTo(1);
                    assertThat(response.getTotalPages()).isEqualTo(0);
                    assertThat(response.getItems()).isEmpty();
                });
    }

    @Test
    public void postPostsWithBlankDate() {
        PostPostsRequest request = postPostsRequest();
        request.setDate(" ");

        editorExecutor().postPosts(request).statusCode(400);

        anonymousExecutor().getPosts(null, null)
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getPage()).isEqualTo(1);
                    assertThat(response.getTotalPages()).isEqualTo(0);
                    assertThat(response.getItems()).isEmpty();
                });
    }

    @Test
    public void postPostsWithNullVisible() {
        PostPostsRequest request = postPostsRequest();
        request.setVisible(null);

        editorExecutor().postPosts(request).statusCode(400);

        anonymousExecutor().getPosts(null, null)
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getPage()).isEqualTo(1);
                    assertThat(response.getTotalPages()).isEqualTo(0);
                    assertThat(response.getItems()).isEmpty();
                });
    }

    @Test
    public void postPostsWithNullCategory() {
        PostPostsRequest request = postPostsRequest();
        request.setCategories(null);

        editorExecutor().postPosts(request).statusCode(400);

        anonymousExecutor().getPosts(null, null)
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getPage()).isEqualTo(1);
                    assertThat(response.getTotalPages()).isEqualTo(0);
                    assertThat(response.getItems()).isEmpty();
                });
    }

    @Test
    public void postPostsWithEmptyCategory() {
        PostPostsRequest request = postPostsRequest();
        request.setCategories(List.of());

        editorExecutor().postPosts(request).statusCode(400);

        anonymousExecutor().getPosts(null, null)
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getPage()).isEqualTo(1);
                    assertThat(response.getTotalPages()).isEqualTo(0);
                    assertThat(response.getItems()).isEmpty();
                });
    }

    @Test
    public void putPostsByIdAsEditor() {
        PostDto postDto = postPosts(postPostsRequest());

        List<CategoryDto> newCategories = List.of(
                generateCategoryDto("3", "Category3"),
                generateCategoryDto("4", "Category4")
        );

        PutPostsRequest request = builders().post().request().putPosts()
                .title(RandomStringUtils.randomAlphanumeric(30))
                .summary(RandomStringUtils.randomAlphanumeric(200))
                .text(RandomStringUtils.randomAlphanumeric(5000))
                .date(Instant.now().toString())
                .visible(true)
                .categories(newCategories)
                .build();

        editorExecutor().putPostsById(postDto.getId(), request)
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getId()).isEqualTo(postDto.getId());
                    assertThat(response.getTitle()).isEqualTo(request.getTitle());
                    assertThat(response.getSummary()).isEqualTo(request.getSummary());
                    assertThat(response.getText()).isEqualTo(request.getText());
                    assertThat(response.getDate()).isEqualTo(request.getDate());
                    assertThat(response.getVisible()).isEqualTo(request.getVisible());
                    assertThat(response.getCategories()).containsExactlyInAnyOrderElementsOf(request.getCategories());
                });
    }

    @Test
    public void putPostsByIdMakeNotVisible() {
        PostDto postDto = postPosts(postPostsRequest());

        PutPostsRequest request = putPostsRequest(postDto);
        request.setVisible(false);

        editorExecutor().putPostsById(postDto.getId(), request)
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getId()).isEqualTo(postDto.getId());
                    assertThat(response.getTitle()).isEqualTo(request.getTitle());
                    assertThat(response.getSummary()).isEqualTo(request.getSummary());
                    assertThat(response.getText()).isEqualTo(request.getText());
                    assertThat(response.getDate()).isEqualTo(request.getDate());
                    assertThat(response.getVisible()).isEqualTo(request.getVisible());
                    assertThat(response.getCategories()).containsExactlyInAnyOrderElementsOf(request.getCategories());
                });

        anonymousExecutor().getPostsById(postDto.getId())
                .statusCode(404);
    }

    @Test
    public void putPostsAsAnonymous() {
        PostDto postDto = postPosts(postPostsRequest());

        anonymousExecutor().putPostsById(postDto.getId(), putPostsRequest(postDto)).statusCode(401);

        anonymousExecutor().getPostsById(postDto.getId())
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getId()).isEqualTo(postDto.getId());
                    assertThat(response.getTitle()).isEqualTo(postDto.getTitle());
                    assertThat(response.getSummary()).isEqualTo(postDto.getSummary());
                    assertThat(response.getText()).isEqualTo(postDto.getText());
                    assertThat(response.getDate()).isEqualTo(postDto.getDate());
                    assertThat(response.getVisible()).isEqualTo(postDto.getVisible());
                    assertThat(response.getCategories()).containsExactlyInAnyOrderElementsOf(postDto.getCategories());
                });
    }

    @Test
    public void putPostsWithNoBody() {
        PostDto postDto = postPosts(postPostsRequest());

        editorExecutor().putPostsById(postDto.getId(), null).statusCode(400);

        anonymousExecutor().getPostsById(postDto.getId())
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getId()).isEqualTo(postDto.getId());
                    assertThat(response.getTitle()).isEqualTo(postDto.getTitle());
                    assertThat(response.getSummary()).isEqualTo(postDto.getSummary());
                    assertThat(response.getText()).isEqualTo(postDto.getText());
                    assertThat(response.getDate()).isEqualTo(postDto.getDate());
                    assertThat(response.getVisible()).isEqualTo(postDto.getVisible());
                    assertThat(response.getCategories()).containsExactlyInAnyOrderElementsOf(postDto.getCategories());
                });
    }

    @Test
    public void putPostsWithNullTitle() {
        PostDto postDto = postPosts(postPostsRequest());

        PutPostsRequest request = putPostsRequest(postDto);
        request.setTitle(null);

        editorExecutor().putPostsById(postDto.getId(), request).statusCode(400);

        anonymousExecutor().getPostsById(postDto.getId())
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getId()).isEqualTo(postDto.getId());
                    assertThat(response.getTitle()).isEqualTo(postDto.getTitle());
                    assertThat(response.getSummary()).isEqualTo(postDto.getSummary());
                    assertThat(response.getText()).isEqualTo(postDto.getText());
                    assertThat(response.getDate()).isEqualTo(postDto.getDate());
                    assertThat(response.getVisible()).isEqualTo(postDto.getVisible());
                    assertThat(response.getCategories()).containsExactlyInAnyOrderElementsOf(postDto.getCategories());
                });
    }

    @Test
    public void putPostsWithBlankTitle() {
        PostDto postDto = postPosts(postPostsRequest());

        PutPostsRequest request = putPostsRequest(postDto);
        request.setTitle(" ");

        editorExecutor().putPostsById(postDto.getId(), request).statusCode(400);

        anonymousExecutor().getPostsById(postDto.getId())
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getId()).isEqualTo(postDto.getId());
                    assertThat(response.getTitle()).isEqualTo(postDto.getTitle());
                    assertThat(response.getSummary()).isEqualTo(postDto.getSummary());
                    assertThat(response.getText()).isEqualTo(postDto.getText());
                    assertThat(response.getDate()).isEqualTo(postDto.getDate());
                    assertThat(response.getVisible()).isEqualTo(postDto.getVisible());
                    assertThat(response.getCategories()).containsExactlyInAnyOrderElementsOf(postDto.getCategories());
                });
    }

    @Test
    public void putPostsWithNullSummary() {
        PostDto postDto = postPosts(postPostsRequest());

        PutPostsRequest request = putPostsRequest(postDto);
        request.setSummary(null);

        editorExecutor().putPostsById(postDto.getId(), request).statusCode(400);

        anonymousExecutor().getPostsById(postDto.getId())
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getId()).isEqualTo(postDto.getId());
                    assertThat(response.getTitle()).isEqualTo(postDto.getTitle());
                    assertThat(response.getSummary()).isEqualTo(postDto.getSummary());
                    assertThat(response.getText()).isEqualTo(postDto.getText());
                    assertThat(response.getDate()).isEqualTo(postDto.getDate());
                    assertThat(response.getVisible()).isEqualTo(postDto.getVisible());
                    assertThat(response.getCategories()).containsExactlyInAnyOrderElementsOf(postDto.getCategories());
                });
    }

    @Test
    public void putPostsWithBlankSummary() {
        PostDto postDto = postPosts(postPostsRequest());

        PutPostsRequest request = putPostsRequest(postDto);
        request.setSummary(" ");

        editorExecutor().putPostsById(postDto.getId(), request).statusCode(400);

        anonymousExecutor().getPostsById(postDto.getId())
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getId()).isEqualTo(postDto.getId());
                    assertThat(response.getTitle()).isEqualTo(postDto.getTitle());
                    assertThat(response.getSummary()).isEqualTo(postDto.getSummary());
                    assertThat(response.getText()).isEqualTo(postDto.getText());
                    assertThat(response.getDate()).isEqualTo(postDto.getDate());
                    assertThat(response.getVisible()).isEqualTo(postDto.getVisible());
                    assertThat(response.getCategories()).containsExactlyInAnyOrderElementsOf(postDto.getCategories());
                });
    }

    @Test
    public void putPostsWithNullText() {
        PostDto postDto = postPosts(postPostsRequest());

        PutPostsRequest request = putPostsRequest(postDto);
        request.setText(null);

        editorExecutor().putPostsById(postDto.getId(), request).statusCode(400);

        anonymousExecutor().getPostsById(postDto.getId())
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getId()).isEqualTo(postDto.getId());
                    assertThat(response.getTitle()).isEqualTo(postDto.getTitle());
                    assertThat(response.getSummary()).isEqualTo(postDto.getSummary());
                    assertThat(response.getText()).isEqualTo(postDto.getText());
                    assertThat(response.getDate()).isEqualTo(postDto.getDate());
                    assertThat(response.getVisible()).isEqualTo(postDto.getVisible());
                    assertThat(response.getCategories()).containsExactlyInAnyOrderElementsOf(postDto.getCategories());
                });
    }

    @Test
    public void putPostsWithBlankText() {
        PostDto postDto = postPosts(postPostsRequest());

        PutPostsRequest request = putPostsRequest(postDto);
        request.setText(" ");

        editorExecutor().putPostsById(postDto.getId(), request).statusCode(400);

        anonymousExecutor().getPostsById(postDto.getId())
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getId()).isEqualTo(postDto.getId());
                    assertThat(response.getTitle()).isEqualTo(postDto.getTitle());
                    assertThat(response.getSummary()).isEqualTo(postDto.getSummary());
                    assertThat(response.getText()).isEqualTo(postDto.getText());
                    assertThat(response.getDate()).isEqualTo(postDto.getDate());
                    assertThat(response.getVisible()).isEqualTo(postDto.getVisible());
                    assertThat(response.getCategories()).containsExactlyInAnyOrderElementsOf(postDto.getCategories());
                });
    }

    @Test
    public void putPostsWithNullDate() {
        PostDto postDto = postPosts(postPostsRequest());

        PutPostsRequest request = putPostsRequest(postDto);
        request.setDate(null);

        editorExecutor().putPostsById(postDto.getId(), request).statusCode(400);

        anonymousExecutor().getPostsById(postDto.getId())
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getId()).isEqualTo(postDto.getId());
                    assertThat(response.getTitle()).isEqualTo(postDto.getTitle());
                    assertThat(response.getSummary()).isEqualTo(postDto.getSummary());
                    assertThat(response.getText()).isEqualTo(postDto.getText());
                    assertThat(response.getDate()).isEqualTo(postDto.getDate());
                    assertThat(response.getVisible()).isEqualTo(postDto.getVisible());
                    assertThat(response.getCategories()).containsExactlyInAnyOrderElementsOf(postDto.getCategories());
                });
    }

    @Test
    public void putPostsWithBlankDate() {
        PostDto postDto = postPosts(postPostsRequest());

        PutPostsRequest request = putPostsRequest(postDto);
        request.setDate(" ");

        editorExecutor().putPostsById(postDto.getId(), request).statusCode(400);

        anonymousExecutor().getPostsById(postDto.getId())
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getId()).isEqualTo(postDto.getId());
                    assertThat(response.getTitle()).isEqualTo(postDto.getTitle());
                    assertThat(response.getSummary()).isEqualTo(postDto.getSummary());
                    assertThat(response.getText()).isEqualTo(postDto.getText());
                    assertThat(response.getDate()).isEqualTo(postDto.getDate());
                    assertThat(response.getVisible()).isEqualTo(postDto.getVisible());
                    assertThat(response.getCategories()).containsExactlyInAnyOrderElementsOf(postDto.getCategories());
                });
    }

    @Test
    public void putPostsWithNullVisible() {
        PostDto postDto = postPosts(postPostsRequest());

        PutPostsRequest request = putPostsRequest(postDto);
        request.setVisible(null);

        editorExecutor().putPostsById(postDto.getId(), request).statusCode(400);

        anonymousExecutor().getPostsById(postDto.getId())
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getId()).isEqualTo(postDto.getId());
                    assertThat(response.getTitle()).isEqualTo(postDto.getTitle());
                    assertThat(response.getSummary()).isEqualTo(postDto.getSummary());
                    assertThat(response.getText()).isEqualTo(postDto.getText());
                    assertThat(response.getDate()).isEqualTo(postDto.getDate());
                    assertThat(response.getVisible()).isEqualTo(postDto.getVisible());
                    assertThat(response.getCategories()).containsExactlyInAnyOrderElementsOf(postDto.getCategories());
                });
    }

    @Test
    public void putPostsWithNullCategory() {
        PostDto postDto = postPosts(postPostsRequest());

        PutPostsRequest request = putPostsRequest(postDto);
        request.setCategories(null);

        editorExecutor().putPostsById(postDto.getId(), request).statusCode(400);

        anonymousExecutor().getPostsById(postDto.getId())
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getId()).isEqualTo(postDto.getId());
                    assertThat(response.getTitle()).isEqualTo(postDto.getTitle());
                    assertThat(response.getSummary()).isEqualTo(postDto.getSummary());
                    assertThat(response.getText()).isEqualTo(postDto.getText());
                    assertThat(response.getDate()).isEqualTo(postDto.getDate());
                    assertThat(response.getVisible()).isEqualTo(postDto.getVisible());
                    assertThat(response.getCategories()).containsExactlyInAnyOrderElementsOf(postDto.getCategories());
                });
    }

    @Test
    public void putPostsWithEmptyCategory() {
        PostDto postDto = postPosts(postPostsRequest());

        PutPostsRequest request = putPostsRequest(postDto);
        request.setCategories(List.of());

        editorExecutor().putPostsById(postDto.getId(), request).statusCode(400);

        anonymousExecutor().getPostsById(postDto.getId())
                .statusCode(200)
                .responseConsumer(response -> {
                    assertThat(response.getId()).isEqualTo(postDto.getId());
                    assertThat(response.getTitle()).isEqualTo(postDto.getTitle());
                    assertThat(response.getSummary()).isEqualTo(postDto.getSummary());
                    assertThat(response.getText()).isEqualTo(postDto.getText());
                    assertThat(response.getDate()).isEqualTo(postDto.getDate());
                    assertThat(response.getVisible()).isEqualTo(postDto.getVisible());
                    assertThat(response.getCategories()).containsExactlyInAnyOrderElementsOf(postDto.getCategories());
                });
    }

    @Test
    public void deletePostsByIdAsAnonymous() {
        PostDto postDto = postPosts(postPostsRequest());

        anonymousExecutor().deletePostsById(postDto.getId()).statusCode(401);
        anonymousExecutor().getPostsById(postDto.getId()).statusCode(200);
    }

    @Test
    public void deletePostsByIdAsEditor() {
        PostDto postDto = postPosts(postPostsRequest());

        editorExecutor().deletePostsById(postDto.getId()).statusCode(204);
        anonymousExecutor().getPostsById(postDto.getId()).statusCode(404);
    }

    @Test
    public void deletePostsByIdWithoutId() {
        PostDto postDto = postPosts(postPostsRequest());

        editorExecutor().deletePostsById(null).statusCode(404);
        anonymousExecutor().getPostsById(postDto.getId()).statusCode(200);
    }

    @Test
    public void deletePostsByIdWithIdThatDoNotExist() {
        PostDto postDto = postPosts(postPostsRequest());

        editorExecutor().deletePostsById(RandomStringUtils.randomAlphanumeric(10)).statusCode(404);
        anonymousExecutor().getPostsById(postDto.getId()).statusCode(200);
    }

    @Test
    public void postPostsWithCategoryThatDoesNotExist() {
        PostPostsRequest postPostsRequest = postPostsRequest();
        postPostsRequest.setCategories(List.of(
                generateCategoryDto(categories.get(0).getId(), categories.get(0).getName()),
                generateCategoryDto("-1", RandomStringUtils.randomAlphanumeric(10)))
        );

        editorExecutor().postPosts(postPostsRequest)
                .statusCode(404)
                .message(format("Category was not found by identifier '%s'", "-1"));
    }

    @Test
    public void putPostsWithCategoryThatDoesNotExist() {
        PostDto postDto = postPosts(postPostsRequest());
        PutPostsRequest putPostsRequest = putPostsRequest(postDto);
        putPostsRequest.setCategories(List.of(
                categories.get(0),
                generateCategoryDto("-1", RandomStringUtils.randomAlphanumeric(10)))
        );

        editorExecutor().putPostsById(postDto.getId(), putPostsRequest)
                .statusCode(404)
                .message(format("Category was not found by identifier '%s'", "-1"));
    }

    private PostPostsRequest postPostsRequest() {
        return builders().post().request().postPosts()
                .title(RandomStringUtils.randomAlphanumeric(30))
                .summary(RandomStringUtils.randomAlphanumeric(200))
                .text(RandomStringUtils.randomAlphanumeric(5000))
                .date(Instant.now().toString())
                .visible(true)
                .categories(categories)
                .build();
    }

    private PostDto postPosts(PostPostsRequest request) {
        return editorExecutor().postPosts(request).statusCode(201).body();
    }

    private PutPostsRequest putPostsRequest(PostDto postDto) {
        return builders().post().request().putPosts()
                .title(postDto.getTitle())
                .summary(postDto.getSummary())
                .text(postDto.getText())
                .date(postDto.getDate())
                .visible(true)
                .categories(postDto.getCategories())
                .build();
    }

    private CategoryDto postCategories(CategoryDto categoryDto) {
        PostCategoriesRequest request = builders().category().request().postCategories()
                .name(categoryDto.getName())
                .build();

        return editorExecutor().postCategories(request).statusCode(201).body();
    }

    private CategoryDto generateCategoryDto(String id, String name) {
        return CategoryDto.builder()
                .id(id)
                .name(name)
                .build();
    }
}