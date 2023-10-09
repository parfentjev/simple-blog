package ee.fakeplastictrees.blog.post;

import ee.fakeplastictrees.blog.post.model.Post;
import ee.fakeplastictrees.blog.post.model.PostDto;
import ee.fakeplastictrees.blog.post.model.PostPreviewDto;

import static ee.fakeplastictrees.blog.core.Utils.builders;

public class PostMappers {
    public PostPreviewDto postToPostPreviewDto(Post post) {
        return builders().post().postPreviewDto()
                .id(post.getId())
                .title(post.getTitle())
                .summary(post.getSummary())
                .date(post.getDate())
                .category(post.getCategory())
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
                .category(post.getCategory())
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
                .category(post.getCategory())
                .build();
    }
}
