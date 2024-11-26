package ee.fakeplastictrees.blog.service.media.exception;

import ee.fakeplastictrees.blog.service.core.exceiption.PublicException;
import ee.fakeplastictrees.blog.service.core.model.MessageDefinition;
import org.springframework.http.HttpStatus;

public class MediaException extends PublicException {
  public MediaException(String message, MessageDefinition definition, HttpStatus status) {
    super(message, definition, status);
  }
}
