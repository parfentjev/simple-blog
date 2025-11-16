package ee.fakeplastictrees.blog.file.model.mapper;

import ee.fakeplastictrees.blog.file.model.File;
import ee.fakeplastictrees.blog.file.model.FileDto;

public class FileMapper {
  public static FileDto fileToDto(File file) {
    return new FileDto(
        file.getId(), file.getOriginalFilename(), file.getContentType(), file.getUploadedAt());
  }
}
