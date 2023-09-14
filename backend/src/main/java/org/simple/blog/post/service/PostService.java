package org.simple.blog.post.service;

import org.simple.blog.core.model.PageDto;
import org.simple.blog.post.model.Post;
import org.simple.blog.post.model.PostDto;
import org.simple.blog.post.model.PostPreviewDto;
import org.simple.blog.post.repository.PostRepository;
import org.simple.blog.core.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public PageDto<PostPreviewDto> getPosts(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("date").descending());
        Page<Post> postsPage = postRepository.findAll(pageRequest);

        List<PostPreviewDto> postPreviewDtoList = postsPage
                .stream()
                .map(post -> Utils.mappers().post().postToPostPreviewDto(post))
                .toList();

        return Utils.builders().<PostPreviewDto>pageDto()
                .page(postsPage.getNumber() + 1)
                .totalPages(postsPage.getTotalPages())
                .items(postPreviewDtoList)
                .build();
    }

    public Optional<PostDto> getPost(String postId) {
        return postRepository.findById(postId).map(post -> Utils.mappers().post().postToPostDto(post));
    }
}
