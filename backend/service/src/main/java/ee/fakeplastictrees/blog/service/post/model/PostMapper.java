package ee.fakeplastictrees.blog.service.post.model;

import ee.fakeplastictrees.blog.codegen.model.PostDto;
import ee.fakeplastictrees.blog.codegen.model.PostEditorDto;
import ee.fakeplastictrees.blog.codegen.model.PostPreviewDto;
import lombok.experimental.UtilityClass;

import java.time.ZoneOffset;

@UtilityClass
public class PostMapper {
  public Post editorDtoToPost(PostEditorDto postEditorDto) {
    return Post.builder()
      .id(postEditorDto.getId())
      .title(postEditorDto.getTitle())
      .summary(postEditorDto.getSummary())
      .text(postEditorDto.getText())
      .date(postEditorDto.getDate().toInstant())
      .visible(postEditorDto.getVisible())
      .build();
  }

  public PostPreviewDto postToPreviewDto(Post post) {
    return new PostPreviewDto()
      .id(post.getId())
      .title(post.getTitle())
      .summary(post.getSummary())
      .date(post.getDate().atOffset(ZoneOffset.UTC))
      .visible(post.getVisible());
  }

  public PostDto postToDto(Post post) {
    return new PostDto()
      .id(post.getId())
      .title(post.getTitle())
      .summary(post.getSummary())
      .text(post.getText())
      .date(post.getDate().atOffset(ZoneOffset.UTC))
      .visible(post.getVisible());
  }
}
