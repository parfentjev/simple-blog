package ee.fakeplastictrees.blog.service.user.exception;

import ee.fakeplastictrees.blog.service.core.exceiption.PublicException;
import ee.fakeplastictrees.blog.service.core.model.MessageDefinition;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends PublicException {
  public UserNotFoundException() {
    super("user was not found", MessageDefinition.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
  }
}
