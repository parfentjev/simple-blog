package ee.fakeplastictrees.user.exception;

import ee.fakeplastictrees.core.exceiption.PublicException;
import ee.fakeplastictrees.core.model.MessageDefinition;
import org.springframework.http.HttpStatus;

public class UsernameAlreadyTakenException extends PublicException {
  public UsernameAlreadyTakenException() {
    super("username is already taken", MessageDefinition.USERNAME_ALREADY_TAKEN, HttpStatus.CONFLICT);
  }
}
