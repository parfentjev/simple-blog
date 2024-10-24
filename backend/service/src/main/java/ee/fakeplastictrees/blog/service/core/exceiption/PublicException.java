package ee.fakeplastictrees.blog.service.core.exceiption;

import ee.fakeplastictrees.blog.service.core.model.MessageDefinition;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PublicException extends RuntimeException {
  private final MessageDefinition definition;
  private final HttpStatus status;

  public PublicException(String message, MessageDefinition definition, HttpStatus status) {
    super(message);

    this.definition = definition;
    this.status = status;
  }
}
