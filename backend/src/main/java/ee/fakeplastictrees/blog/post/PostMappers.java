package ee.fakeplastictrees.blog.post;

import ee.fakeplastictrees.blog.core.Utils;
import ee.fakeplastictrees.blog.post.model.Post;
import ee.fakeplastictrees.blog.post.model.PostDto;
import ee.fakeplastictrees.blog.post.model.PostPreviewDto;

public class PostMappers {
    public PostPreviewDto postToPostPreviewDto(Post post) {
        return Utils.builders().post().postPreviewDto()
                .id(post.getId())
                .title(post.getTitle())
                .summary(post.getSummary())
                .date(post.getDate())
                .build();
    }

    public PostDto postToPostDto(Post post) {
        return Utils.builders().post().postDto()
                .id(post.getId())
                .title(post.getTitle())
                .summary(post.getSummary())
                .text(post.getText())
                .date(post.getDate())
                .build();
    }
}
