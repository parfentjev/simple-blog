package ee.fakeplastictrees.blog.service.media.model;

import ee.fakeplastictrees.blog.service.core.exceiption.PublicException;
import ee.fakeplastictrees.blog.service.core.exceiption.PublicExceptionFactory;
import ee.fakeplastictrees.blog.service.core.model.MessageDefinition;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

@UtilityClass
public class MediaExceptionFactory {
  public PublicException emptyFile() {
    return PublicExceptionFactory.withData("file is empty", MessageDefinition.FILE_EMPTY, HttpStatus.BAD_REQUEST);
  }

  public PublicException saveFailed() {
    return PublicExceptionFactory.withData("failed to save the media file", MessageDefinition.FILE_FAILED_SAVE, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public PublicException loadFailed() {
    return PublicExceptionFactory.withData("resource not found", MessageDefinition.FILE_FAILED_LOAD, HttpStatus.NOT_FOUND);
  }
}
