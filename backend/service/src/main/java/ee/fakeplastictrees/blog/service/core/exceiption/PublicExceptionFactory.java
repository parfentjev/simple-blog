package ee.fakeplastictrees.blog.service.core.exceiption;

import ee.fakeplastictrees.blog.service.core.model.MessageDefinition;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

@UtilityClass
public class PublicExceptionFactory {
  public PublicException withData(String message, MessageDefinition definition, HttpStatus status) {
    return new PublicException(message, definition, status);
  }
}
