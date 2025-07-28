package ee.fakeplastictrees.blog.file.model;


public class FileMapper {
  public static FileDto resourceToDto(File file) {
    return new FileDto(file.getId(), file.getOriginalFilename(), file.getContentType(), file.getUploadedAt());
  }
}
