package ee.fakeplastictrees.blog.post.service;

import ee.fakeplastictrees.blog.category.model.Category;
import ee.fakeplastictrees.blog.category.service.CategoryService;
import ee.fakeplastictrees.blog.core.exceptions.ResourceNotFoundException;
import ee.fakeplastictrees.blog.core.model.PageDto;
import ee.fakeplastictrees.blog.post.model.Post;
import ee.fakeplastictrees.blog.post.model.PostDto;
import ee.fakeplastictrees.blog.post.model.PostPreviewDto;
import ee.fakeplastictrees.blog.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static ee.fakeplastictrees.blog.core.Utils.builders;
import static ee.fakeplastictrees.blog.core.Utils.mappers;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryService categoryService;

    public PageDto<PostPreviewDto> getPosts(Integer page, Integer size) {
        PageRequest pageRequest = buildPageRequest(page, size);
        Page<Post> postsPage = postRepository.findByVisibleTrue(pageRequest);

        return buildPageDto(postsPage);
    }

    public PageDto<PostPreviewDto> getPosts(Integer page, Integer size, String category) {
        PageRequest pageRequest = buildPageRequest(page, size);
        Page<Post> postsPage = postRepository.findByVisibleTrueAndCategoryIgnoreCase(pageRequest, category);

        return buildPageDto(postsPage);
    }

    private PageRequest buildPageRequest(Integer page, Integer size) {
        return PageRequest.of(page - 1, size, Sort.by("date").descending());
    }

    private PageDto<PostPreviewDto> buildPageDto(Page<Post> postsPage) {
        List<PostPreviewDto> postPreviewDtoList = postsPage
                .stream()
                .map(post -> mappers().post().postToPostPreviewDto(post))
                .toList();

        return builders().<PostPreviewDto>pageDto()
                .page(postsPage.getNumber() + 1)
                .totalPages(postsPage.getTotalPages())
                .items(postPreviewDtoList)
                .build();
    }

    public Optional<PostDto> getPost(String postId, boolean includeDrafts) {
        return postRepository.findById(postId)
                .filter(post -> post.getVisible() || includeDrafts)
                .map(post -> mappers().post().postToPostDto(post));
    }

    public PostDto createPost(PostDto postDto) {
        assertCategoriesExist(postDto.getCategory());

        return mappers().post().postToPostDto(postRepository.save(mappers().post().postDtoToPost(postDto)));
    }

    public PostDto updatePost(PostDto postDto) {
        if (!postRepository.existsById(postDto.getId())) {
            throw new ResourceNotFoundException(Post.class, postDto.getId());
        }

        assertCategoriesExist(postDto.getCategory());

        return mappers().post().postToPostDto(postRepository.save(mappers().post().postDtoToPost(postDto)));
    }

    public void deletePost(String postId) {
        postRepository.findById(postId).ifPresentOrElse(post -> postRepository.delete(post), () -> {
            throw new ResourceNotFoundException(Post.class, postId);
        });
    }

    private void assertCategoriesExist(Set<String> categories) {
        categories.forEach(category -> {
            if (categoryService.getCategoryByName(category).isEmpty()) {
                throw new ResourceNotFoundException(Category.class, category);
            }
        });
    }
}
