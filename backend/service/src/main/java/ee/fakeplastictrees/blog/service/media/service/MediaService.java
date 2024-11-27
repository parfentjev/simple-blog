package ee.fakeplastictrees.blog.service.media.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static java.lang.String.format;

@Service
public class MediaService {
  @Value("${media.upload.directory}")
  private String uploadDirectory;

  public String save(MultipartFile file) {
    if (file == null || file.isEmpty()) {
      throw MediaExceptionFactory.emptyFile();
    }

    var targetDirectory = Paths.get(this.uploadDirectory);
    var targetFile = targetDirectory
      .resolve(generateFilePath(file))
      .normalize()
      .toAbsolutePath();

    if (fileOutsideTargetDirectory(targetDirectory, targetFile)) {
      throw MediaExceptionFactory.saveFailed();
    }

    try (var inputStream = file.getInputStream()) {
      Files.copy(inputStream, targetFile);
    } catch (IOException e) {
      throw MediaExceptionFactory.saveFailed();
    }

    return targetFile.getFileName().toString();
  }

  private boolean fileOutsideTargetDirectory(Path targetDirectory, Path targetFile) {
    return !targetFile.getParent().equals(targetDirectory.toAbsolutePath());
  }

  private Path generateFilePath(MultipartFile file) {
    if (file.getOriginalFilename() == null) {
      throw MediaExceptionFactory.saveFailed();
    }

    var uuid = UUID.randomUUID().toString();
    var split = file.getOriginalFilename().split("\\.");
    var extension = split.length > 1 ? "." + split[split.length - 1] : "";
    return Paths.get(format("%s%s", uuid, extension));
  }
}
