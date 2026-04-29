package ee.fakeplastictrees.blog.post.model.mapper;

import java.util.List;
import java.util.Optional;

import ee.fakeplastictrees.blog.post.model.Post;
import ee.fakeplastictrees.blog.post.model.PostDto;
import ee.fakeplastictrees.blog.post.model.PostEditorDto;
import ee.fakeplastictrees.blog.post.model.PostPreviewDto;
import ee.fakeplastictrees.blog.post.model.PostTagDto;

public class PostMapper {
  public static PostPreviewDto postToPreviewDto(Post post) {
    return postToPreviewDto(post, List.of());
  }

  public static PostPreviewDto postToPreviewDto(Post post, List<PostTagDto> tags) {
    return new PostPreviewDto(
        post.getId(),
        post.getTitle(),
        post.getSlug(),
        post.getPreviewText(),
        post.getDate(),
        post.getVisible(),
        hasMore(post),
        tags);
  }

  public static PostDto postToDto(Post post) {
    return new PostDto(
        post.getId(),
        post.getTitle(),
        post.getSlug(),
        post.getPreviewText(),
        post.getFullText(),
        post.getDate(),
        post.getVisible(),
        hasMore(post));
  }

  private static boolean hasMore(Post post) {
    return post.getFullText() != null && !post.getFullText().isBlank();
  }

  public static Post editorDtoToPost(PostEditorDto postEditorDto) {
    var post = new Post();
    post.setTitle(postEditorDto.title());
    post.setPreviewText(postEditorDto.summary());
    post.setFullText(postEditorDto.text());
    post.setVisible(Optional.ofNullable(postEditorDto.visible()).orElse(false));

    return post;
  }
}
