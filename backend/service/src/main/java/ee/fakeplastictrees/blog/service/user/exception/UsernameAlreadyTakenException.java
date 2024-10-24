package ee.fakeplastictrees.blog.service.user.exception;

import ee.fakeplastictrees.blog.service.core.exceiption.PublicException;
import ee.fakeplastictrees.blog.service.core.model.MessageDefinition;
import org.springframework.http.HttpStatus;

public class UsernameAlreadyTakenException extends PublicException {
  public UsernameAlreadyTakenException() {
    super("username is already taken", MessageDefinition.USERNAME_ALREADY_TAKEN, HttpStatus.CONFLICT);
  }
}
