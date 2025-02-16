package ee.fakeplastictrees.blog.service.media.model;

import ee.fakeplastictrees.blog.codegen.model.MediaDto;
import lombok.experimental.UtilityClass;

import java.time.ZoneOffset;

@UtilityClass
public class MediaMapper {
  public MediaDto mediaToDto(Media media) {
    return new MediaDto()
      .id(media.getId())
      .originalFilename(media.getOriginalFilename())
      .contentType(media.getContentType())
      .date(media.getUploadedAt().atOffset(ZoneOffset.UTC));
  }
}
