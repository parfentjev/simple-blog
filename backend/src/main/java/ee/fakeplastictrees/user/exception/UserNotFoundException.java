package ee.fakeplastictrees.user.exception;

import ee.fakeplastictrees.core.exceiption.PublicException;
import ee.fakeplastictrees.core.model.MessageDefinition;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends PublicException {
  public UserNotFoundException() {
    super("user was not found", MessageDefinition.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
  }
}
