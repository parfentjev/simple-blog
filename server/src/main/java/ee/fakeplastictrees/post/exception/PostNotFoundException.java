package ee.fakeplastictrees.post.exception;

import ee.fakeplastictrees.core.exceiption.PublicException;
import ee.fakeplastictrees.core.model.MessageDefinition;
import org.springframework.http.HttpStatus;

public class PostNotFoundException extends PublicException {
  public PostNotFoundException() {
    super("post was not found", MessageDefinition.POST_NOT_FOUND, HttpStatus.NOT_FOUND);
  }
}
