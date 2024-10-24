package ee.fakeplastictrees.blog.service.post.exception;

import ee.fakeplastictrees.blog.service.core.exceiption.PublicException;
import ee.fakeplastictrees.blog.service.core.model.MessageDefinition;
import org.springframework.http.HttpStatus;

public class PostNotFoundException extends PublicException {
  public PostNotFoundException() {
    super("post was not found", MessageDefinition.POST_NOT_FOUND, HttpStatus.NOT_FOUND);
  }
}
