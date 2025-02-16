package ee.fakeplastictrees.blog.service.media.service;

import ee.fakeplastictrees.blog.codegen.model.PageMediaDto;
import ee.fakeplastictrees.blog.service.core.model.PageRequestFactory;
import ee.fakeplastictrees.blog.service.media.model.*;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MediaService {
  private static final int PAGE_SIZE = 100;

  @Value("${media.upload.directory}")
  private String uploadDirectory;

  @Autowired
  private MediaRepository mediaRepository;

  @Autowired
  private ResourceLoader resourceLoader;

  public String saveFile(MultipartFile inputFile) {
    if (inputFile == null || inputFile.isEmpty()) {
      throw MediaExceptionFactory.emptyFile();
    }

    return mediaRepository.save(Media.builder()
        .originalFilename(inputFile.getOriginalFilename())
        .path(saveToDisk(inputFile).toAbsolutePath().toString())
        .contentType(inputFile.getContentType())
        .uploadedAt(Instant.now())
        .build())
      .getId();
  }

  private Path saveToDisk(MultipartFile inputFile) {
    var outputFile = Paths.get(this.uploadDirectory)
      .resolve(Paths.get(format("%d-%s", Instant.now().toEpochMilli(), UUID.randomUUID())))
      .normalize()
      .toAbsolutePath();

    try (var inputStream = inputFile.getInputStream()) {
      Files.copy(inputStream, outputFile);
    } catch (IOException e) {
      throw MediaExceptionFactory.saveFailed();
    }

    return outputFile;
  }

  public ResourceDto getFile(String id) {
    var media = mediaRepository.findById(id).orElseThrow(MediaExceptionFactory::loadFailed);
    var resource = resourceLoader.getResource("file:" + media.getPath());

    return ResourceDto.builder().contentType(media.getContentType()).resource(resource).build();
  }

  public PageMediaDto getFiles(Integer page) {
    var mediaPage = mediaRepository.findAll(PageRequestFactory.withPage(page, PAGE_SIZE, "uploadedAt"));

    return new PageMediaDto()
      .page(page)
      .totalPages(mediaPage.getTotalPages())
      .items(mediaPage.get().map(MediaMapper::mediaToDto).toList());
  }
}
