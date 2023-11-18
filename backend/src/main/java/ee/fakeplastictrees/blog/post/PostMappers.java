package ee.fakeplastictrees.blog.post;

import ee.fakeplastictrees.blog.post.model.Post;
import ee.fakeplastictrees.blog.post.model.PostDto;
import ee.fakeplastictrees.blog.post.model.PostPreviewDto;

import static ee.fakeplastictrees.blog.core.Utils.builders;
import static ee.fakeplastictrees.blog.core.Utils.mappers;

public class PostMappers {
    public PostPreviewDto postToPostPreviewDto(Post post) {
        return builders().post().postPreviewDto()
                .id(post.getId())
                .title(post.getTitle())
                .summary(post.getSummary())
                .date(post.getDate())
                .categories(post.getCategories().stream().map(i -> mappers().category().categoryToCategoryDto(i)).toList())
                .build();
    }

    public PostPreviewDto postDtoToPostPreviewDto(PostDto postDto) {
        return builders().post().postPreviewDto()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .summary(postDto.getSummary())
                .date(postDto.getDate())
                .categories(postDto.getCategories())
                .build();
    }

    public PostDto postToPostDto(Post post) {
        return builders().post().postDto()
                .id(post.getId())
                .title(post.getTitle())
                .summary(post.getSummary())
                .text(post.getText())
                .date(post.getDate())
                .visible(post.getVisible())
                .categories(post.getCategories().stream().map(i -> mappers().category().categoryToCategoryDto(i)).toList())
                .build();
    }

    public Post postDtoToPost(PostDto post) {
        return builders().post().post()
                .id(post.getId())
                .title(post.getTitle())
                .summary(post.getSummary())
                .text(post.getText())
                .date(post.getDate())
                .visible(post.getVisible())
                .categories(post.getCategories().stream().map(i -> mappers().category().categoryDtoToCategory(i)).toList())
                .build();
    }
}
