package org.simple.blog.post;

import org.simple.blog.post.model.Post;
import org.simple.blog.post.model.PostDto;
import org.simple.blog.post.model.PostPreviewDto;
import org.simple.blog.core.Utils;

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
