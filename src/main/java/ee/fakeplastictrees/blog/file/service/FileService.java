package ee.fakeplastictrees.blog.file.service;

import ee.fakeplastictrees.blog.core.exceiption.ResourceNotFoundException;
import ee.fakeplastictrees.blog.core.model.PageRequestFactory;
import ee.fakeplastictrees.blog.file.exception.DeleteFileException;
import ee.fakeplastictrees.blog.file.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.UUID;

import static java.lang.String.format;

@Service
public class FileService {
  private static final int PAGE_SIZE = 100;

  @Value("${media.upload.directory}")
  private String uploadDirectory;

  private final FileRepository fileRepository;

  private final ResourceLoader resourceLoader;

  public FileService(FileRepository fileRepository, ResourceLoader resourceLoader) {
    this.fileRepository = fileRepository;
    this.resourceLoader = resourceLoader;
  }

  public String saveFile(MultipartFile inputFile) {
    if (inputFile == null || inputFile.isEmpty()) {
      throw new RuntimeException();
    }

    var media = new File();
    media.setOriginalFilename(inputFile.getOriginalFilename());
    media.setPath(saveToDisk(inputFile).toAbsolutePath().toString());
    media.setContentType(inputFile.getContentType());
    media.setUploadedAt(Instant.now());

    return fileRepository.save(media).getId();
  }

  private Path saveToDisk(MultipartFile inputFile) {
    var outputFile = Paths.get(this.uploadDirectory)
      .resolve(Paths.get(format("%d-%s", Instant.now().toEpochMilli(), UUID.randomUUID())))
      .normalize()
      .toAbsolutePath();

    try (var inputStream = inputFile.getInputStream()) {
      Files.copy(inputStream, outputFile);
    } catch (IOException e) {
      throw new RuntimeException();
    }

    return outputFile;
  }

  public void deleteFromDisk(String id) {
    try {
      if (getFile(id).resource().getFile().delete()) {
        fileRepository.deleteById(id);

        return;
      }

      throw new DeleteFileException();
    } catch (IOException e) {
      throw new DeleteFileException(e);
    }
  }

  public ResourceDto getFile(String id) {
    var entity = fileRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    var resource = resourceLoader.getResource("file:" + entity.getPath());

    return new ResourceDto(resource, entity.getContentType());
  }

  public FilePageDto getEditorFiles(Integer pageNumber) {
    var filePage = fileRepository.findAll(PageRequestFactory.withPage(pageNumber, PAGE_SIZE, "uploadedAt"));

    return new FilePageDto(pageNumber, filePage.getTotalPages(), filePage.get().map(FileMapper::resourceToDto).toList());
  }
}
