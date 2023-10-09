package ee.fakeplastictrees.blog.post.service;

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

import static ee.fakeplastictrees.blog.core.Utils.builders;
import static ee.fakeplastictrees.blog.core.Utils.mappers;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public PageDto<PostPreviewDto> getPosts(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("date").descending());
        Page<Post> postsPage = postRepository.findByVisibleTrue(pageRequest);

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

    public Optional<PostDto> getPost(String postId) {
        return postRepository.findByIdAndVisibleTrue(postId).map(post -> mappers().post().postToPostDto(post));
    }

    public PostDto createPost(PostDto postDto) {
        return mappers().post().postToPostDto(postRepository.save(mappers().post().postDtoToPost(postDto)));
    }

    public PostDto updatePost(PostDto postDto) {
        if (!postRepository.existsById(postDto.getId())) {
            throw new ResourceNotFoundException(Post.class, postDto.getId());
        }

        return mappers().post().postToPostDto(postRepository.save(mappers().post().postDtoToPost(postDto)));
    }

    public void deletePost(String postId) {
        postRepository.findById(postId).ifPresentOrElse(post -> postRepository.delete(post), () -> {
            throw new ResourceNotFoundException(Post.class, postId);
        });
    }
}
