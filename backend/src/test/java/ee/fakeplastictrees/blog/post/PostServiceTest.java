package ee.fakeplastictrees.blog.post;

import ee.fakeplastictrees.blog.core.exceptions.ResourceNotFoundException;
import ee.fakeplastictrees.blog.core.model.PageDto;
import ee.fakeplastictrees.blog.post.model.Post;
import ee.fakeplastictrees.blog.post.model.PostDto;
import ee.fakeplastictrees.blog.post.model.PostPreviewDto;
import ee.fakeplastictrees.blog.post.repository.PostRepository;
import ee.fakeplastictrees.blog.post.service.PostService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.Optional;

import static ee.fakeplastictrees.blog.core.Utils.mappers;
import static ee.fakeplastictrees.blog.testsupport.TestSupport.postTestSupport;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    public void getPosts() {
        Post post1 = postTestSupport().generatePost();
        Post post2 = postTestSupport().generatePost();

        when(postRepository.findByVisibleTrue(any())).thenReturn(new PageImpl<>(Arrays.asList(post1, post2)));

        PageDto<PostPreviewDto> pageDto = postService.getPosts(1, 2);
        assertThat(pageDto.getPage()).isEqualTo(1);
        assertThat(pageDto.getTotalPages()).isEqualTo(1);
        assertThat(pageDto.getItems()).containsExactlyInAnyOrder(
                mappers().post().postToPostPreviewDto(post1),
                mappers().post().postToPostPreviewDto(post2)
        );
    }

    @Test
    public void createPost() {
        PostDto inputPostDto = postTestSupport().generatePostDto();
        inputPostDto.setId(null);
        Post inputPost = mappers().post().postDtoToPost(inputPostDto);
        Post outputPost = mappers().post().postDtoToPost(inputPostDto);
        outputPost.setId(RandomStringUtils.randomAlphanumeric(10));

        when(postRepository.save(inputPost)).thenReturn(outputPost);

        PostDto createdPost = postService.createPost(inputPostDto);
        verify(postRepository, times(1)).save(inputPost);
        assertThat(createdPost.getId()).isEqualTo(outputPost.getId());
        assertThat(createdPost.getTitle()).isEqualTo(outputPost.getTitle());
        assertThat(createdPost.getSummary()).isEqualTo(outputPost.getSummary());
        assertThat(createdPost.getText()).isEqualTo(outputPost.getText());
        assertThat(createdPost.getDate()).isEqualTo(outputPost.getDate());
        assertThat(createdPost.getVisible()).isEqualTo(outputPost.getVisible());
        assertThat(createdPost.getCategory()).containsExactlyInAnyOrderElementsOf(outputPost.getCategory());
    }

    @Test
    public void updatePost() {
        PostDto inputPostDto = postTestSupport().generatePostDto();
        Post post = mappers().post().postDtoToPost(inputPostDto);

        when(postRepository.existsById(post.getId())).thenReturn(true);
        when(postRepository.save(post)).thenReturn(post);

        PostDto createdPost = postService.updatePost(inputPostDto);
        verify(postRepository, times(1)).save(post);
        assertThat(createdPost.getId()).isEqualTo(post.getId());
        assertThat(createdPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(createdPost.getSummary()).isEqualTo(post.getSummary());
        assertThat(createdPost.getText()).isEqualTo(post.getText());
        assertThat(createdPost.getDate()).isEqualTo(post.getDate());
        assertThat(createdPost.getVisible()).isEqualTo(post.getVisible());
        assertThat(createdPost.getCategory()).containsExactlyInAnyOrderElementsOf(post.getCategory());
    }

    @Test
    public void updatePostThatDoesNotExist() {
        PostDto postDto = postTestSupport().generatePostDto();

        when(postRepository.existsById(postDto.getId())).thenReturn(false);

        assertThatThrownBy(() -> postService.updatePost(postDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Post was not found by identifier '%s'", postDto.getId());
    }

    @Test
    public void deletePost() {
        String postId = randomAlphanumeric(10);
        Post post = postTestSupport().generatePost();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        postService.deletePost(postId);
        verify(postRepository, times(1)).delete(post);
    }

    @Test
    public void deletePostThatDoesNotExist() {
        String postId = randomAlphanumeric(10);

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.deletePost(postId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Post was not found by identifier '%s'", postId);

        verify(postRepository, times(0)).delete(any());
    }
}
