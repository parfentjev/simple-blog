package ee.fakeplastictrees.blog.service.media.service;

import ee.fakeplastictrees.blog.service.core.model.MessageDefinition;
import ee.fakeplastictrees.blog.service.media.exception.MediaException;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

@UtilityClass
public class MediaExceptionFactory {
  public MediaException emptyFile() {
    return new MediaException("file is empty", MessageDefinition.FILE_EMPTY, HttpStatus.BAD_REQUEST);
  }

  public MediaException saveFailed() {
    return new MediaException("failed to save", MessageDefinition.FILE_FAILED_SAVE, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
