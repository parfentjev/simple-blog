package ee.fakeplastictrees.post.util;

import ee.fakeplastictrees.post.model.Post;
import ee.fakeplastictrees.post.model.PostDto;
import ee.fakeplastictrees.post.model.PostEditorDto;
import ee.fakeplastictrees.post.model.PostPreviewDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PostMapper {
  public Post editorDtoToPost(PostEditorDto postEditorDto) {
    return Post.builder()
      .id(postEditorDto.getId())
      .title(postEditorDto.getTitle())
      .summary(postEditorDto.getSummary())
      .text(postEditorDto.getText())
      .date(postEditorDto.getDate())
      .visible(postEditorDto.getVisible())
      .build();
  }

  public PostPreviewDto postToPreviewDto(Post post) {
    return PostPreviewDto.builder()
      .id(post.getId())
      .title(post.getTitle())
      .summary(post.getSummary())
      .date(post.getDate())
      .visible(post.getVisible())
      .build();
  }

  public PostDto postToDto(Post post) {
    return PostDto.builder()
      .id(post.getId())
      .title(post.getTitle())
      .summary(post.getSummary())
      .text(post.getText())
      .date(post.getDate())
      .visible(post.getVisible())
      .build();
  }
}
