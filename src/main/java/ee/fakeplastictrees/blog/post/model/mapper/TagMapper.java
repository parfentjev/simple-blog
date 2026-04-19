package ee.fakeplastictrees.blog.post.model.mapper;

import ee.fakeplastictrees.blog.post.model.Tag;
import ee.fakeplastictrees.blog.post.model.TagDto;

public class TagMapper {
  public static TagDto tagToTagDto(Tag tag) {
    return new TagDto(tag.getId());
  }
}
