package ee.fakeplastictrees.blog.post.model.mapper;

import ee.fakeplastictrees.blog.post.model.Post;
import ee.fakeplastictrees.blog.post.model.PostDto;
import ee.fakeplastictrees.blog.post.model.PostEditorDto;
import ee.fakeplastictrees.blog.post.model.PostPreviewDto;

public class PostMapper {
  public static PostPreviewDto postToPreviewDto(Post post) {
    return new PostPreviewDto(
      post.getId(),
      post.getTitle(),
      post.getSlug(),
      post.getSummary(),
      post.getDate(),
      post.getVisible()
    );
  }

  public static PostDto postToDto(Post post) {
    return new PostDto(post.getId(),
      post.getTitle(),
      post.getSlug(),
      post.getSummary(),
      post.getText(),
      post.getDate(),
      post.getVisible()
    );
  }

  public static Post editorDtoToPost(PostEditorDto postEditorDto) {
    var post = new Post();
    post.setTitle(postEditorDto.title());
    post.setSummary(postEditorDto.summary());
    post.setText(postEditorDto.text());
    post.setVisible(postEditorDto.visible());

    return post;
  }
}
