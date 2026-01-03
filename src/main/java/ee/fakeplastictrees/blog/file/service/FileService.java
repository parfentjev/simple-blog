package ee.fakeplastictrees.blog.file.service;

import static java.lang.String.format;

import ee.fakeplastictrees.blog.core.exception.HTTPNotFoundException;
import ee.fakeplastictrees.blog.core.model.factory.PageRequestFactory;
import ee.fakeplastictrees.blog.file.exception.FileServiceException;
import ee.fakeplastictrees.blog.file.model.File;
import ee.fakeplastictrees.blog.file.model.FileDto;
import ee.fakeplastictrees.blog.file.model.FileEditorDto;
import ee.fakeplastictrees.blog.file.model.FilePageDto;
import ee.fakeplastictrees.blog.file.model.ResourceDto;
import ee.fakeplastictrees.blog.file.model.factory.FilePageDtoFactory;
import ee.fakeplastictrees.blog.file.model.mapper.FileMapper;
import ee.fakeplastictrees.blog.file.repository.FileRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
  private final FileRepository fileRepository;
  private final ResourceLoader resourceLoader;

  @Value("${files.admin.page.size:100}")
  private Integer pageSize;

  @Value("${files.upload.directory}")
  private String uploadDirectory;

  private static final String sortBy = "uploadedAt";

  public FileService(FileRepository fileRepository, ResourceLoader resourceLoader) {
    this.fileRepository = fileRepository;
    this.resourceLoader = resourceLoader;
  }

  public String saveFile(MultipartFile input) {
    if (input == null || input.isEmpty()) {
      throw new FileServiceException(FileServiceException.EMPTY_FILE);
    }

    var file = new File();
    file.setOriginalFilename(input.getOriginalFilename());
    file.setPath(saveToDisk(input).toAbsolutePath().toString());
    file.setContentType(input.getContentType());
    file.setUploadedAt(Instant.now());

    return fileRepository.save(file).getId();
  }

  public void updateFile(FileEditorDto fileEditorDto) {
    var file = fileRepository.findById(fileEditorDto.id()).orElseThrow(HTTPNotFoundException::new);
    file.setOriginalFilename(fileEditorDto.filename());
    fileRepository.save(file);
  }

  public void deleteFile(String id) {
    try {
      if (getResource(id).resource().getFile().delete()) {
        fileRepository.deleteById(id);

        return;
      }

      throw new FileServiceException(FileServiceException.FAILED_TO_DELETE);
    } catch (IOException e) {
      throw new FileServiceException(FileServiceException.FAILED_TO_DELETE, e);
    }
  }

  public FileDto getFile(String id) throws HTTPNotFoundException {
    var entity = fileRepository.findById(id).orElseThrow(HTTPNotFoundException::new);

    return FileMapper.fileToDto(entity);
  }

  public ResourceDto getResource(String id) throws HTTPNotFoundException {
    var entity = fileRepository.findById(id).orElseThrow(HTTPNotFoundException::new);
    var resource = resourceLoader.getResource("file:" + entity.getPath());

    return new ResourceDto(resource, entity.getContentType());
  }

  public FilePageDto getEditorFiles(Integer pageNumber) {
    var pageable = PageRequestFactory.withPage(pageNumber, pageSize, sortBy);
    var items = fileRepository.findAll(pageable);

    return FilePageDtoFactory.from(pageNumber, items);
  }

  public FilePageDto getEditorFilesByName(String nameContaining, Integer pageNumber) {
    var pageable = PageRequestFactory.withPage(pageNumber, pageSize, sortBy);
    var items = fileRepository.findByOriginalFilenameContainingIgnoreCase(nameContaining, pageable);

    return FilePageDtoFactory.from(pageNumber, items);
  }

  private Path saveToDisk(MultipartFile inputFile) {
    var outputFile =
        Paths.get(this.uploadDirectory)
            .resolve(Paths.get(format("%d-%s", Instant.now().toEpochMilli(), UUID.randomUUID())))
            .normalize()
            .toAbsolutePath();

    try (var inputStream = inputFile.getInputStream()) {
      Files.copy(inputStream, outputFile);
    } catch (IOException e) {
      throw new FileServiceException(FileServiceException.FAILED_TO_SAVE, e);
    }

    return outputFile;
  }
}
