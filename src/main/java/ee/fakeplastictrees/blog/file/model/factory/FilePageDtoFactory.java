package ee.fakeplastictrees.blog.file.model.factory;

import ee.fakeplastictrees.blog.file.model.File;
import ee.fakeplastictrees.blog.file.model.FilePageDto;
import ee.fakeplastictrees.blog.file.model.mapper.FileMapper;
import org.springframework.data.domain.Page;

public class FilePageDtoFactory {
  public static FilePageDto from(Integer pageNumber, Page<File> filePage) {
    var totalPages = filePage.getTotalPages();
    var files = filePage.get().map(FileMapper::fileToDto).toList();

    return new FilePageDto(pageNumber, totalPages, files);
  }
}
