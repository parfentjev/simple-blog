package ee.fakeplastictrees.blog.service.media.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static java.lang.String.format;

@Service
public class MediaService {
  @Value("${media.upload.directory}")
  private String uploadDirectory;

  public void save(String parentId, MultipartFile file) {
    if (file == null || file.isEmpty()) {
      throw MediaExceptionFactory.emptyFile();
    }

    var targetDirectory = Paths.get(this.uploadDirectory);
    var targetFile = targetDirectory
      .resolve(Paths.get(format("%s-%s", parentId, file.getOriginalFilename())))
      .normalize()
      .toAbsolutePath();

    if (!targetFile.getParent().equals(targetDirectory.toAbsolutePath())) {
      throw MediaExceptionFactory.saveFailed();
    }

    try (var inputStream = file.getInputStream()) {
      Files.copy(inputStream, targetFile, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw MediaExceptionFactory.saveFailed();
    }
  }
}
